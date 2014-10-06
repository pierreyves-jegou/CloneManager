package caillou.company.clonemanager.background.bean.impl;



import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.io.File;
import java.nio.file.Path;


public class MyFile extends File implements ApplicationFile{

    /**
     * Serial
     */
    private static final long serialVersionUID = -8255870717251417044L;

    private Group.VALUE groupValue = null;

    private String filteredName = null;

    private String MD5Print = null;

    private String partialMD5Print = null;

    private Long size = null;

    private String roughlyName = null;

    @Override
    public String getBaseNamePath(){
        return this.getName();
    }
    
    @Override
    public Group.VALUE getGroup() {
        return groupValue;
    }

    public void setGroupValue(Group.VALUE group) {
        this.groupValue = group;
    }

    public String getFilteredName() {
        return filteredName;
    }

    public void setFilteredName(String filteredName) {
        this.filteredName = filteredName;
    }

    @Override
    public String getMD5Print() {
        return MD5Print;
    }

    @Override
    public void setMD5Print(String mD5Print) {
        MD5Print = mD5Print;
    }

    @Override
    public String getPartialMD5Print() {
        return partialMD5Print;
    }

    @Override
    public void setPartialMD5Print(String partialMD5Print) {
        this.partialMD5Print = partialMD5Print;
    }

    @Override
    public Long getSize() {
        if(size == null){
            size = this.length();
        }
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getRoughlyName() {
        return roughlyName;
    }

    public void setRoughlyName(String roughlyName) {
        this.roughlyName = roughlyName;
    }

    /**
     * ** Constructors / Getters / Setters **
     * @param pathname
     * @param groupValue
     */
    public MyFile(String pathname, Group.VALUE groupValue) {
        super(pathname);
        this.groupValue = groupValue;
    }

    public MyFile(Path path, Group.VALUE groupValue) {
        super(path.toAbsolutePath().toUri());
        this.groupValue = groupValue;
    }

    public MyFile(Path path) {
        super(path.toAbsolutePath().toUri());
    }

    public MyFile(String pathname) {
        super(pathname);
    }

    @Override
    public File getEnclosingFile() {
        return this;
    }
}
