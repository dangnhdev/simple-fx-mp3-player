
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class StatusBar extends BorderPane {

    private MediaPlayer player;
    public Label timeLabel;
    private Slider time;
    private final Image image = new Image("http://icons.iconarchive.com/icons/icons8/ios7/24/Time-And-Date-Stopwatch-icon.png");
    private final ImageView imgView = new ImageView(image);

    public StatusBar(){}
    public StatusBar(MediaPlayer play) {
        this.player = play;
        setPadding(new Insets(5, 18, 5, 18));
        setStyle("-fx-background-color: #DCDCDC");
        
        //Listener for time label
        player.currentTimeProperty().addListener( ov -> {
            timeLabel.setText(formatTime(player.getCurrentTime(), player.getTotalDuration()));
        });
        timeLabel = new Label("00 : 00");
        
        time = new Slider();
        //Listener for time slider
        player.currentTimeProperty().addListener( ov -> {
            time.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
        });

        //Seek by slider function
        time.valueProperty().addListener( ov -> {
            if (time.isPressed()) {
                player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100));
            }
        });
        setCenter(time);
        setAlignment(time, Pos.CENTER);
        setRight(timeLabel);
        setAlignment(timeLabel, Pos.CENTER);
        setLeft(imgView);
        setAlignment(imgView, Pos.CENTER);
        HBox.setHgrow(time, Priority.ALWAYS); //set time slider to Alway fit pane
        setPadding(new Insets(5,10,5,10));
        setStyle("-fx-background-color: #DCDCDC");
    }

    //Format time Label, copy from idrsolutionsblog
    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d / %d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d / %02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d : %02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d : %02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }
    public Label getTimeLabel() {
        return timeLabel;
    }
    public Slider getTime() {
        return time;
    }

    public void setTime(Slider time) {
        this.time = time;
    }
}
