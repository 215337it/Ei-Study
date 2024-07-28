import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskScheduler {
    private List<Command> commandList = new ArrayList<>();

    public void takeCommand(Command command) {
        commandList.add(command);
    }

    public void executeCommands() {
        for (Command command : commandList) {
            command.execute();
        }
        commandList.clear();
    }
}

interface Command {
    void execute();
}

class TurnOnCommand implements Command {
    private SmartDevice device;

    public TurnOnCommand(SmartDevice device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.turnOn();
    }
}

class TurnOffCommand implements Command {
    private SmartDevice device;

    public TurnOffCommand(SmartDevice device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.turnOff();
    }
}
