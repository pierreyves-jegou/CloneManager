package caillou.company.clonemanager.background.service.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.filter.Filter;
import caillou.company.clonemanager.background.bean.filter.FilterGroup;
import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.background.bean.impl.MyFile;
import caillou.company.clonemanager.background.exception.CloneManagerException;
import caillou.company.clonemanager.background.exception.CloneManagerIOException;
import caillou.company.clonemanager.background.log.DebugMessage;
import caillou.company.clonemanager.background.log.ErrorMessage;
import caillou.company.clonemanager.background.service.contract.Cancellable;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

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
    private Group.VALUE currentGroup;
    private Long bytesToTreat = (long) 0;
    private Cancellable callingThread;
    
    private static final Logger log = Logger.getLogger(FileVisitor.class.getName());
    
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e)
            throws IOException {
        
        log.error(ErrorMessage.VISIT_FILE_FAILED + file);
        
        if(this.callingThread.isCancelled()){
            log.debug(DebugMessage.CANCEL_BY_USER);
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.SKIP_SUBTREE;
    }

    /**
     * Invoked for a file in a directory.
     * @param path
     * @param attrs
     * @return 
     * @throws java.io.IOException
     */
    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs)
            throws IOException {
        if(this.callingThread.isCancelled()){
            log.debug(DebugMessage.CANCEL_BY_USER);
            return FileVisitResult.TERMINATE;
        }
         
        MyFile myFile = new MyFile(path);
        if (!myFile.isDirectory()) {
            if (filter == null || filter.accept(myFile)) {
                myFile.setGroupValue(currentGroup);
                Long fileSize = attrs.size();
                myFile.setSize(fileSize);
                bytesToTreat += fileSize;
                filesToTreat.add(myFile);
            }
        }

        return FileVisitResult.CONTINUE;
    }

    public void visit(Cancellable object, Path path, Group.VALUE groupValue)
            throws CloneManagerException, IOException {

        setCurrentGroup(groupValue);
        this.callingThread = object;
        
        if (!path.toFile().exists()) {
            log.error(ErrorMessage.FILE_MATCHING_PATH_DOESNT_EXIST + path);
            throw new CloneManagerIOException(
                    ErrorMessage.FILE_MATCHING_PATH_DOESNT_EXIST + path, path.toString());
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

    public void setCurrentGroup(Group.VALUE currentGroup) {
        this.currentGroup = currentGroup;
    }

    public Long getBytesToTreat() {
        return bytesToTreat;
    }
}
