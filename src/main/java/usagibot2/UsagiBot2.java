package usagibot2;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.formdev.flatlaf.FlatDarkLaf;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import usagibot2.GUI.GuiLogAppender;

import javax.swing.*;

@Slf4j
public class UsagiBot2 {

    public static void main(String[] args) {

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        GuiLogAppender guiAppender = new GuiLogAppender();
        guiAppender.setContext(context);
        guiAppender.start();

        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(guiAppender);

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            SwingUtilities.invokeLater(() -> {
                // Place Window Here
            });
            log.info("Testing");
        } catch (Exception ex) {
            log.error("Failed to initialize LaF!");
        }
    }
}
