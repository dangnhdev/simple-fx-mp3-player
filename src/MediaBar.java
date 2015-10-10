
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaBar extends BorderPane {
    private MediaPlayer player;
    private Slider vol;
    private Button btPlay;
    private Button btNext;
    private Button btPrevious;
    private final ToggleButton volume;
    private final StatusBar statusBar = new StatusBar();
    private ImageView iv;
    private ImageView volumeIv;
    private ImageView nextIv;
    private ImageView previousIv;
    private Image imgPlay = new Image("http://icons.iconarchive.com/icons/danieledesantis/audio-video-outline/24/play-icon.png");
    private Image imgPause = new Image("http://icons.iconarchive.com/icons/danieledesantis/audio-video-outline/24/pause-icon.png");
    private Image imgVolume = new Image("http://icons.iconarchive.com/icons/icons8/ios7/16/Media-Controls-High-Volume-icon.png");
    private Image mute = new Image("http://icons.iconarchive.com/icons/icons8/ios7/16/Media-Controls-Mute-icon.png");
    private Image next = new Image("http://icons.iconarchive.com/icons/iconsmind/outline/16/Next-2-2-icon.png");
    private Image previous = new Image("http://icons.iconarchive.com/icons/iconsmind/outline/16/Previous-icon.png");
    public MediaBar(MediaPlayer play) {
        player = play;
        //Create Button Play
        btPlay = new Button(); 
        iv = new ImageView(imgPlay);
        btPlay.setGraphic(iv);
        btPlay.setPrefWidth(30);
        //Create Button Next
        btNext = new Button();
        nextIv = new ImageView(next);
        btNext.setGraphic(nextIv);
        //Create Button Previous
        btPrevious = new Button();
        previousIv = new ImageView(previous);
        btPrevious.setGraphic(previousIv);
        
        vol = new Slider();
        volume = new ToggleButton();
        volumeIv = new ImageView(imgVolume);
        volume.setGraphic(volumeIv);
        setPadding(new Insets(5));
        volume.setContentDisplay(ContentDisplay.RIGHT);
        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        setStyle("-fx-background-color: #DCDCDC");
        HBox left = new HBox(btPrevious, btPlay, btNext);
        HBox right = new HBox(volume, vol);

        left.setAlignment(Pos.CENTER);
        right.setAlignment(Pos.CENTER);
        left.setSpacing(10);
        right.setSpacing(5);
        left.setPadding(new Insets(5, 10, 5, 10));
        right.setPadding(new Insets(5, 10, 5, 10));
        setLeft(left);
        setRight(right);
        vol.setValue(50);
        player.volumeProperty().bind(vol.valueProperty().divide(100));
        //Volume action
        volume.setOnAction( e -> {
            if (!player.isMute()) {
                player.setMute(true);
                volumeIv.setImage(mute);
            }else {
                player.setMute(false);
                volumeIv.setImage(imgVolume);
            }
        });
        //Button Play action
        btPlay.setOnAction(e -> {
            Status status = player.getStatus();
            if (status == Status.PLAYING) {
                player.pause();
                iv.setImage(imgPlay);
            }
            if (status == Status.PAUSED || status == Status.READY) {
                player.play();
                iv.setImage(imgPause);
            }
        });
        //Play or pause when space pressed
        btPlay.setOnKeyTyped(e -> {
            Status status = player.getStatus();
            if (e.getCode() == KeyCode.SPACE) {
                if (status == Status.PLAYING) {
                    player.pause();
                    btPlay.setText(">");
                }
                if (status == Status.PAUSED || status == Status.READY) {
                    player.play();
                    btPlay.setText("||");
                }
            }
        });
    }

    //getter and setter
    public Slider getVol() {
        return vol;
    }

    public void setVol(Slider vol) {
        this.vol = vol;
    }
//	public Button getBtPlay() {
//		return btPlay;
//	}
//	public void setBtPlay(Button btPlay) {
//		this.btPlay = btPlay;
//	}



    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public Button getBtPlay() {
        return btPlay;
    }

    public void setBtPlay(Button btPlay) {
        this.btPlay = btPlay;
    }

    public ImageView getIv() {
        return iv;
    }

    public Image getImgPlay() {
        return imgPlay;
    }

    public Image getImgPause() {
        return imgPause;
    }

    public Button getBtNext() {
        return btNext;
    }

    public Button getBtPrevious() {
        return btPrevious;
    }

}
