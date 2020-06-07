import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import javax.sound.sampled.*;


public class AlarmClock extends JFrame {
    private JLabel currentTimeLabel, timeLabel, dateLabel;
    private JLabel setAlarmLabel;
    private JTextField alarmHoursField,alarmMinutesField, alarmSecondsField;
    private JButton alarmButton;
    private JLabel alarmStatus;

    private int seconds, minutes, hours;
    private boolean verify = false;

    AlarmClock() {
        this.timeSet();
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setTitle("Alarm Clock");
        super.setSize(500, 180);
        super.setLocation(500, 100);

        Font font1 = new Font(" ",Font.BOLD, 20);
        Font font2 = new Font(" ",Font.BOLD, 16);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 3));

        FlowLayout layout = new FlowLayout();

        JPanel panel2 = new JPanel();
        panel2.setLayout(layout);

        JPanel panel3 = new JPanel();
        panel3.setLayout(layout);

        currentTimeLabel = new JLabel("Current time");
        currentTimeLabel.setFont(font1);

        timeLabel = new JLabel("Time");
        timeLabel.setFont(font1);

        dateLabel = new JLabel("Date");
        dateLabel.setFont(font1);

        setAlarmLabel = new JLabel("Set alarm: ");
        setAlarmLabel.setFont(font2);

        alarmHoursField = new JTextField(2);
        alarmMinutesField = new JTextField(2);
        alarmSecondsField = new JTextField(2);

        alarmButton = new JButton("SET");
        alarmButton.setBackground(new java.awt.Color(255, 10, 69));
        alarmButton.setSize(10,56);
        alarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AlarmButtonActionPerformed(evt);
            }
        });

        alarmStatus = new JLabel("Alarm is not set.");
        alarmStatus.setFont(font1);

        panel1.add(currentTimeLabel);
        panel1.add(timeLabel);
        panel1.add(dateLabel);

        panel2.add(setAlarmLabel);
        panel2.add(alarmHoursField);
        panel2.add(alarmMinutesField);
        panel2.add(alarmSecondsField);
        panel2.add(alarmButton);

        panel3.add(alarmStatus);

        add(panel1, "North");
        add(panel2);
        add(panel3, "South");

        setVisible(true);
    }

    public void timeSet(){
        Timer time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Date d = new Date();
                SimpleDateFormat sdf1 = new SimpleDateFormat("k : mm : ss");
                String timeDate = sdf1.format(d);

                timeLabel.setText(timeDate);
                int sc = d.getSeconds();
                int mn = d.getMinutes();
                int hr = d.getHours();
                if (seconds == sc && minutes == mn && hours == hr){
                    System.out.print("Time matched!");
                    verify = false;

                    Component Jframe = null;
                    try {
                        File soundFile = new File("C:\\Users\\Esta\\IdeaProjects\\practice\\alarm_clock\\alarm_sounds\\gentle_alarm.wav");
                        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                        Clip clip = AudioSystem.getClip();
                        clip.open(ais);

                        clip.setFramePosition(0);
                        clip.start();

                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
                        exc.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(Jframe,"Alarm is ringing!","Alarm Clock",JOptionPane.PLAIN_MESSAGE);
                    alarmStatus.setText("Alarm is not set.");
                }

                SimpleDateFormat sdf2 = new SimpleDateFormat("dd / MM / Y");
                String dateDate = sdf2.format(d);

                dateLabel.setText(dateDate);
            }
        });
        time.start();
    }

    private void AlarmButtonActionPerformed(ActionEvent evt) {
        try {
            hours = Integer.parseInt(alarmHoursField.getText());
            minutes = Integer.parseInt(alarmMinutesField.getText());
            seconds = Integer.parseInt(alarmSecondsField.getText());
            if (hours >= 1 && hours <= 23 && minutes >= 0 && minutes <= 59 && seconds >= 0 && seconds <= 59){
                verify = true;
                alarmHoursField.setText("");
                alarmMinutesField.setText("");
                alarmSecondsField.setText("");
                alarmStatus.setText(" Alarm is set on " + hours + " : " + minutes + " : " + seconds);
            }else {
                alarmStatus.setText("Please, input the right time (Example: 10:12:00)");
                alarmHoursField.setText("");
                alarmMinutesField.setText("");
                alarmSecondsField.setText("");
            }

        }catch (Exception e){
            alarmStatus.setText("Please, input the right time (Example: 10:12:00)");
            alarmHoursField.setText("");
            alarmMinutesField.setText("");
            alarmSecondsField.setText("");
            return;
        };
    }
}