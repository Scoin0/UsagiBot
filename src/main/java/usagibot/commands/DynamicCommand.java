package usagibot.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DynamicCommand extends Command {

    private String output;

    @JsonCreator
    public DynamicCommand(@JsonProperty("name") String name, @JsonProperty("output") String output) {
        this.name = name;
        this.output = output;
    }

    @Override
    public void onCommand(CommandEvent event) {
        event.getClient().sendMessage(output);
    }
}