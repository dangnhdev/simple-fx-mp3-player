
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends VBox {

    private Media media;
    private MediaPlayer player;
    private MediaView view;
    private Pane mpane;
    private MediaBar bar;
    private ListView<Music> playList;
    private MenuBar menu;
    private StatusBar statusBar;

    //Constructor

    public Player() {
    }

    public Player(String file, MenuBar menu, ListView<Music> playList) {
        getChildren().clear();
        this.media = new Media(file);
        this.player = new MediaPlayer(this.media);
        this.view = new MediaView(this.player);
        this.bar = new MediaBar(this.player);
        this.statusBar = new StatusBar(this.player);
//		setBottom(this.bar);
        this.setMenu(menu);
        this.playList = playList;
        getChildren().addAll(menu, playList, bar, statusBar);
    }

    //Play, pause, stop function

    public void play() {
        player.play();
    }

    public void pause() {
        player.pause();
    }

    public void stop() {
        player.stop();
    }

    //getter and setter
    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public MediaView getView() {
        return view;
    }

    public void setView(MediaView view) {
        this.view = view;
    }

    public Pane getMpane() {
        return mpane;
    }

    public void setMpane(Pane mpane) {
        this.mpane = mpane;
    }

    public MediaBar getBar() {
        return bar;
    }

    public void setBar(MediaBar bar) {
        this.bar = bar;
    }

    public ListView<Music> getPlayList() {
        return playList;
    }

    public void setPlayList(ListView<Music> playList) {
        this.playList = playList;
    }

    public MenuBar getMenu() {
        return menu;
    }

    public void setMenu(MenuBar menu) {
        this.menu = menu;
    }
}
