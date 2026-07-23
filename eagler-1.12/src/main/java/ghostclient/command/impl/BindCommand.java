package ghostclient.command.impl;

import java.util.HashMap;
import java.util.Map;

import ghostclient.GhostClient;
import ghostclient.command.Command;
import ghostclient.module.Module;
import net.lax1dude.eaglercraft.KeyboardConstants;

public class BindCommand extends Command {

    private static final Map<String, Integer> KEY_MAP = new HashMap<>();

    static {
        KEY_MAP.put("NONE", 0);
        KEY_MAP.put("NULL", 0);
        KEY_MAP.put("A", KeyboardConstants.KEY_A);
        KEY_MAP.put("B", KeyboardConstants.KEY_B);
        KEY_MAP.put("C", KeyboardConstants.KEY_C);
        KEY_MAP.put("D", KeyboardConstants.KEY_D);
        KEY_MAP.put("E", KeyboardConstants.KEY_E);
        KEY_MAP.put("F", KeyboardConstants.KEY_F);
        KEY_MAP.put("G", KeyboardConstants.KEY_G);
        KEY_MAP.put("H", KeyboardConstants.KEY_H);
        KEY_MAP.put("I", KeyboardConstants.KEY_I);
        KEY_MAP.put("J", KeyboardConstants.KEY_J);
        KEY_MAP.put("K", KeyboardConstants.KEY_K);
        KEY_MAP.put("L", KeyboardConstants.KEY_L);
        KEY_MAP.put("M", KeyboardConstants.KEY_M);
        KEY_MAP.put("N", KeyboardConstants.KEY_N);
        KEY_MAP.put("O", KeyboardConstants.KEY_O);
        KEY_MAP.put("P", KeyboardConstants.KEY_P);
        KEY_MAP.put("Q", KeyboardConstants.KEY_Q);
        KEY_MAP.put("R", KeyboardConstants.KEY_R);
        KEY_MAP.put("S", KeyboardConstants.KEY_S);
        KEY_MAP.put("T", KeyboardConstants.KEY_T);
        KEY_MAP.put("U", KeyboardConstants.KEY_U);
        KEY_MAP.put("V", KeyboardConstants.KEY_V);
        KEY_MAP.put("W", KeyboardConstants.KEY_W);
        KEY_MAP.put("X", KeyboardConstants.KEY_X);
        KEY_MAP.put("Y", KeyboardConstants.KEY_Y);
        KEY_MAP.put("Z", KeyboardConstants.KEY_Z);
        KEY_MAP.put("1", KeyboardConstants.KEY_1);
        KEY_MAP.put("2", KeyboardConstants.KEY_2);
        KEY_MAP.put("3", KeyboardConstants.KEY_3);
        KEY_MAP.put("4", KeyboardConstants.KEY_4);
        KEY_MAP.put("5", KeyboardConstants.KEY_5);
        KEY_MAP.put("6", KeyboardConstants.KEY_6);
        KEY_MAP.put("7", KeyboardConstants.KEY_7);
        KEY_MAP.put("8", KeyboardConstants.KEY_8);
        KEY_MAP.put("9", KeyboardConstants.KEY_9);
        KEY_MAP.put("0", KeyboardConstants.KEY_0);
        KEY_MAP.put("SPACE", KeyboardConstants.KEY_SPACE);
        KEY_MAP.put("LSHIFT", KeyboardConstants.KEY_LSHIFT);
        KEY_MAP.put("RSHIFT", KeyboardConstants.KEY_RSHIFT);
        KEY_MAP.put("LCONTROL", KeyboardConstants.KEY_LCONTROL);
        KEY_MAP.put("RCONTROL", KeyboardConstants.KEY_RCONTROL);
        KEY_MAP.put("LMENU", KeyboardConstants.KEY_LMENU);
        KEY_MAP.put("LALT", KeyboardConstants.KEY_LMENU);
        KEY_MAP.put("RMENU", KeyboardConstants.KEY_RMENU);
        KEY_MAP.put("RALT", KeyboardConstants.KEY_RMENU);
        KEY_MAP.put("TAB", KeyboardConstants.KEY_TAB);
        KEY_MAP.put("RETURN", KeyboardConstants.KEY_RETURN);
        KEY_MAP.put("ENTER", KeyboardConstants.KEY_RETURN);
        KEY_MAP.put("BACK", KeyboardConstants.KEY_BACK);
        KEY_MAP.put("BACKSPACE", KeyboardConstants.KEY_BACK);
        KEY_MAP.put("ESCAPE", KeyboardConstants.KEY_ESCAPE);
        KEY_MAP.put("ESC", KeyboardConstants.KEY_ESCAPE);
        KEY_MAP.put("GRAVE", KeyboardConstants.KEY_GRAVE);
        KEY_MAP.put("MINUS", KeyboardConstants.KEY_MINUS);
        KEY_MAP.put("EQUALS", KeyboardConstants.KEY_EQUALS);
        KEY_MAP.put("LBRACKET", KeyboardConstants.KEY_LBRACKET);
        KEY_MAP.put("RBRACKET", KeyboardConstants.KEY_RBRACKET);
        KEY_MAP.put("SEMICOLON", KeyboardConstants.KEY_SEMICOLON);
        KEY_MAP.put("APOSTROPHE", KeyboardConstants.KEY_APOSTROPHE);
        KEY_MAP.put("BACKSLASH", KeyboardConstants.KEY_BACKSLASH);
        KEY_MAP.put("COMMA", KeyboardConstants.KEY_COMMA);
        KEY_MAP.put("PERIOD", KeyboardConstants.KEY_PERIOD);
        KEY_MAP.put("SLASH", KeyboardConstants.KEY_SLASH);
        KEY_MAP.put("F1", KeyboardConstants.KEY_F1);
        KEY_MAP.put("F2", KeyboardConstants.KEY_F2);
        KEY_MAP.put("F3", KeyboardConstants.KEY_F3);
        KEY_MAP.put("F4", KeyboardConstants.KEY_F4);
        KEY_MAP.put("F5", KeyboardConstants.KEY_F5);
        KEY_MAP.put("F6", KeyboardConstants.KEY_F6);
        KEY_MAP.put("F7", KeyboardConstants.KEY_F7);
        KEY_MAP.put("F8", KeyboardConstants.KEY_F8);
        KEY_MAP.put("F9", KeyboardConstants.KEY_F9);
        KEY_MAP.put("F10", KeyboardConstants.KEY_F10);
        KEY_MAP.put("F11", KeyboardConstants.KEY_F11);
        KEY_MAP.put("F12", KeyboardConstants.KEY_F12);
        KEY_MAP.put("NUMPAD0", KeyboardConstants.KEY_NUMPAD0);
        KEY_MAP.put("NUMPAD1", KeyboardConstants.KEY_NUMPAD1);
        KEY_MAP.put("NUMPAD2", KeyboardConstants.KEY_NUMPAD2);
        KEY_MAP.put("NUMPAD3", KeyboardConstants.KEY_NUMPAD3);
        KEY_MAP.put("NUMPAD4", KeyboardConstants.KEY_NUMPAD4);
        KEY_MAP.put("NUMPAD5", KeyboardConstants.KEY_NUMPAD5);
        KEY_MAP.put("NUMPAD6", KeyboardConstants.KEY_NUMPAD6);
        KEY_MAP.put("NUMPAD7", KeyboardConstants.KEY_NUMPAD7);
        KEY_MAP.put("NUMPAD8", KeyboardConstants.KEY_NUMPAD8);
        KEY_MAP.put("NUMPAD9", KeyboardConstants.KEY_NUMPAD9);
        KEY_MAP.put("INSERT", KeyboardConstants.KEY_INSERT);
        KEY_MAP.put("DELETE", KeyboardConstants.KEY_DELETE);
        KEY_MAP.put("HOME", KeyboardConstants.KEY_HOME);
        KEY_MAP.put("END", KeyboardConstants.KEY_END);
        KEY_MAP.put("PRIOR", KeyboardConstants.KEY_PRIOR);
        KEY_MAP.put("PAGEUP", KeyboardConstants.KEY_PRIOR);
        KEY_MAP.put("NEXT", KeyboardConstants.KEY_NEXT);
        KEY_MAP.put("PAGEDOWN", KeyboardConstants.KEY_NEXT);
        KEY_MAP.put("UP", KeyboardConstants.KEY_UP);
        KEY_MAP.put("DOWN", KeyboardConstants.KEY_DOWN);
        KEY_MAP.put("LEFT", KeyboardConstants.KEY_LEFT);
        KEY_MAP.put("RIGHT", KeyboardConstants.KEY_RIGHT);
    }

    public BindCommand() {
        super("bind", "Bind a key to a module. Usage: .bind <module> <key|none>");
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 2) return "\u00a7cUsage: .bind <module> <key>";
        String name = args[0];
        String keyName = args[1].toUpperCase();
        Module module = GhostClient.MODULES.getByName(name);
        if (module == null) return "\u00a7cModule not found: " + name;

        Integer key = KEY_MAP.get(keyName);
        if (key == null) return "\u00a7cUnknown key: " + keyName;
        module.setKeybind(key);
        if (key == 0) {
            return "\u00a7aUnbound " + module.getName() + ".";
        }
        return "\u00a7aBound " + module.getName() + " to " + keyName + ".";
    }
}
