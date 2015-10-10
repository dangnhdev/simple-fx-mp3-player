
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    private Player player;
    private FileChooser fileChooser;
    private File file, dir;
    private List<File> listFile = new ArrayList<>();
    private ListView<Music> playList;
    private List<Music> listMusic = new ArrayList<>();
    private MenuBar menu;
    private int songID = -1;
    private Stage myStage;

    @Override
    public void start(final Stage primaryStage) throws Exception {
		//Create menu bar

        final MenuItem openFile = new MenuItem("Open File");
        final MenuItem openDirectory = new MenuItem("Open Directory");
        final Menu fileMenu = new Menu("Open");
        fileMenu.getItems().add(openFile);
        fileMenu.getItems().add(openDirectory);
        menu = new MenuBar();
        menu.getMenus().add(fileMenu);

        //Set Default player
//        listMusic.add(new Music(new File("F:/Ahrix.mp3")));
        playList = new ListView<>(FXCollections.observableArrayList(listMusic));
        player = new Player("http://www.stephaniequinn.com/Music/Handel%20-%20Entrance%20of%20the%20Queen%20of%20Sheba.mp3", menu, playList);
        primaryStage.setScene(new Scene(player,350,500));
        primaryStage.setTitle("Simple FX Mp3 Player v1.0");
        primaryStage.getIcons().add(new Image("http://www.shuttlemusicplayer.com/wp-content/uploads/2014/08/icon.png"));
        myStage = primaryStage;
        primaryStage.setMaxHeight(500);
        primaryStage.setMinHeight(152);
        primaryStage.show();

        //Open Dir function
        DirectoryChooser dChooser = new DirectoryChooser();
        openDirectory.setOnAction(e -> {
            dir = dChooser.showDialog(primaryStage);
            if (dir.isDirectory()) {
                String[] extensions = new String[]{"mp3"};
                //Apache Common IO, list all file include sub dir
                listFile = (List<File>) FileUtils.listFiles(dir, extensions, true);
                listMusic.clear();
                for (File file : listFile) {
                    listMusic.add(new Music(file));
                }
                if (!listMusic.isEmpty()) {
                    songID = 0;
                    playSong(listMusic.get(songID));
                }
            }
        });

        //Open single file function
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 Audio File", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);
        openFile.setOnAction(e -> {
            file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                listMusic.clear();
                listMusic.add(new Music(file));
                songID = 0;
                playSong(listMusic.get(songID));
            }
        });
        playList.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (e.getClickCount() == 2) {
                    playSong(playList.getFocusModel().getFocusedItem());
                    songID = playList.getFocusModel().getFocusedIndex();
                }
            }
        });
    }

    public void playSong(Music song) {
        player.getPlayer().dispose();
        playList = new ListView<>(FXCollections.observableArrayList(listMusic));
        player = new Player(song.toURI(), menu, playList);
        player.getPlayList().getFocusModel().focus(songID);
        player.getPlayList().getSelectionModel().select(songID);
        player.getPlayList().scrollTo(songID);
        player.getBar().getIv().setImage(player.getBar().getImgPause());
        player.play();
        player.getPlayList().setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (e.getClickCount() == 2) {
                    songID = playList.getFocusModel().getFocusedIndex();
                    playSong(playList.getFocusModel().getFocusedItem());
                }
            }
        });
        player.getBar().getBtNext().setOnAction( e -> {
            if (songID != listMusic.size() - 1) {
                nextSong();
            }
        });
        player.getBar().getBtPrevious().setOnAction( e -> {
            if (songID != 0) {
                previousSong();
            }
        });
        myStage.setScene(new Scene(player,350,500));
        myStage.setMaxHeight(500);
        myStage.setMinHeight(152);
        player.getPlayList().requestFocus();
        player.getPlayer().setOnEndOfMedia(() -> {
            player.getBar().getIv().setImage(player.getBar().getImgPlay());
            nextSong();
        });
    }

    public void nextSong() {
        if (songID < listMusic.size() - 1) {
            songID++;
            playSong(listMusic.get(songID));
        }
    }
    public void previousSong() {
        if (songID > 0) {
            songID--;
            playSong(listMusic.get(songID));
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
