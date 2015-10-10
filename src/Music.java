
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;

public class Music {

    private String name;
    private File data;

    public Music() {
    }

    public Music(File data) {
        this.data = data;
        this.name = fileNameProcess(data);
    }

    @Override
    public String toString() {
        return name;
    }

    public String toURI() {
        try {
            return data.toURI().toURL().toExternalForm();
        } catch (MalformedURLException e) {
        }
        return "";
    }

    private String fileNameProcess(File file) {
        Path path = file.toPath();
        String fileName = path.getFileName().toString();
        if (fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }
}
