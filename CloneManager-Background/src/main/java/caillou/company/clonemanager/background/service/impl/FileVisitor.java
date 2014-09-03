package caillou.company.clonemanager.background.service.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.filter.Filter;
import caillou.company.clonemanager.background.bean.filter.FilterGroup;
import caillou.company.clonemanager.background.bean.impl.MyFile;
import caillou.company.clonemanager.background.exception.OrganizerException;
import caillou.company.clonemanager.background.service.contract.Cancellable;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

/**
 * Class which visit the file system from a starting point and add to the set
 * 'acceptedFiles' the files which have to be treated Good point :
 * SimpleFileVisitor doesn't keep going on the symbolic link Hard link on
 * directories aren't taking into consideration as only Mac OS X v10.5 (Leopard)
 * allow that (hopefully ;)
 *
 * @author pierre
 *
 */
public class FileVisitor extends SimpleFileVisitor<Path> {

    Set<Path> visitedLink = new HashSet<>();
    Filter<ApplicationFile> filter;
    Set<ApplicationFile> filesToTreat = new HashSet<>();
    private String currentGroup;
    private Long bytesToTreat = new Long(0);
    private Cancellable callingThread;
    
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e)
            throws IOException {
        System.err.printf("Visiting failed for %s\n", file);
        if(this.callingThread.isCancelled()){
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.SKIP_SUBTREE;
    }

    /**
     * Invoked for a file in a directory.
     */
    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs)
            throws IOException {
        if(this.callingThread.isCancelled()){
            System.out.println("Jarrete dans enqueue");
            return FileVisitResult.TERMINATE;
        }
        MyFile myFile = new MyFile(path);
        if (!myFile.isDirectory()) {
            if (filter == null || filter.accept(myFile)) {
                myFile.setGroup(currentGroup);
                Long fileSize = attrs.size();
                myFile.setSize(fileSize);
                bytesToTreat += fileSize;
                filesToTreat.add(myFile);
            }
        }

        return FileVisitResult.CONTINUE;
    }

    public void visit(Cancellable object, Path path, String group)
            throws OrganizerException, IOException {

        setCurrentGroup(group);
        this.callingThread = object;

        if (!path.toFile().exists()) {
            throw new OrganizerException(
                    "The file specified by the given path doesn't exist");
        }

        Files.walkFileTree(path, this);
    }

    /**
     * ** Constructors / Getters / Setters **
     * @param filter
     */
    public FileVisitor(Filter<ApplicationFile> filter) {
        this.filter = filter;
    }

    public void setFilter(Filter<ApplicationFile> filter) {
        this.filter = filter;
    }

    public void addFilter(Filter filter){
        FilterGroup filterGroup = new FilterGroup();
        filterGroup.addFilter(filter);
        filterGroup.addFilter(this.filter);
        this.filter = filterGroup;
    }
    
    public Set<ApplicationFile> getFilesToTreat() {
        return filesToTreat;
    }

    public void setFilesToTreat(Set<ApplicationFile> pFilesToTreat) {
        filesToTreat = pFilesToTreat;
    }

    public void setCurrentGroup(String currentGroup) {
        this.currentGroup = currentGroup;
    }

    public Long getBytesToTreat() {
        return bytesToTreat;
    }
}
