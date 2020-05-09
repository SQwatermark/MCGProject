package moe.gensoukyo.mcgproject.common.feature.hackychat;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.ClientCommandHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 修改原版聊天输入处理，使其能够执行自定义批处理指令
 * 实际上是修改了命令处理类
 * @author drzzm32
 * @date 2020/5/8
 */
public class ClientCmdHacker {

    public static final Pattern CMD_REGEX = Pattern.compile(
        "#([A-Za-z0-9_]+)" +                                // #func
            "(?:\\s)*\\(" +                                 //   (
                "(?:\\s)*([A-Za-z0-9]+)?(?:\\s)*" +         //     param0
                "(?:(?:\\s)*,(?:\\s)*([A-Za-z0-9]+))?" +    //    ,param1
                "(?:(?:\\s)*,(?:\\s)*([A-Za-z0-9]+))?" +    //    ,param2
                "(?:(?:\\s)*,(?:\\s)*([A-Za-z0-9]+))?" +    //    ,param3
                "(?:(?:\\s)*,(?:\\s)*([A-Za-z0-9]+))?" +    //    ,param4
                "(?:(?:\\s)*,(?:\\s)*([A-Za-z0-9]+))?" +    //    ,param5
                "(?:(?:\\s)*,(?:\\s)*([A-Za-z0-9]+))?" +    //    ,param6
                "(?:(?:\\s)*,(?:\\s)*([A-Za-z0-9]+))?" +    //    ,param7
            "(?:\\s)*\\)" +                                 //   )
                    "(?:\\s)*\\{" +                         //     {
                        "(?:\\s)*(.*)" +                    //        body
                    "(?:\\s)*\\}"                           //     }
    );

    public static class Func {

        public static final Func EMPTY = new Func(null);

        public String name;
        public String[] args;
        public String body;

        @Override
        public String toString() {
            return "Func{" +
                    "name='" + name + '\'' +
                    ", args=" + Arrays.toString(args) +
                    ", body='" + body + '\'' +
                    '}';
        }

        public Func() {
            name = "";
            args = new String[8];
            Arrays.fill(args, "");
            body = "";
        }

        private Func(Object arg) {
            if (arg == null) {
                name = null;
                args = null;
                body = null;
            }
        }

        public static Func parseFunc(String input) {
            Matcher matcher = CMD_REGEX.matcher(input);
            if (matcher.matches()) {
                MatchResult result = matcher.toMatchResult();
                Func func = new Func();
                func.name = result.group(1);
                for (int i = 0; i < result.groupCount() - 2; i++) {
                    String now = result.group(i + 2);
                    if (now == null)
                        continue;
                    func.args[i] = now;
                }
                func.body = result.group(result.groupCount());
                return func;
            }
            return EMPTY;
        }

    }

    @FunctionalInterface
    public interface IFunc {
        void run(String body, String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7);
    }
    @FunctionalInterface
    public interface IFunc7 extends IFunc {
        void run(String body, String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6);

        @Override
        default void run(String body, String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
            run(body, arg0, arg1, arg2, arg3, arg4, arg5, arg6);
        }
    }
    @FunctionalInterface
    public interface IFunc6 extends IFunc7 {
        void run(String body, String arg0, String arg1, String arg2, String arg3, String arg4, String arg5);

        @Override
        default void run(String body, String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
            run(body, arg0, arg1, arg2, arg3, arg4, arg5);
        }
    }
    @FunctionalInterface
    public interface IFunc5 extends IFunc6 {
        void run(String body, String arg0, String arg1, String arg2, String arg3, String arg4);

        @Override
        default void run(String body, String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
            run(body, arg0, arg1, arg2, arg3, arg4);
        }
    }
    @FunctionalInterface
    public interface IFunc4 extends IFunc5 {
        void run(String body, String arg0, String arg1, String arg2, String arg3);

        @Override
        default void run(String body, String arg0, String arg1, String arg2, String arg3, String arg4) {
            run(body, arg0, arg1, arg2, arg3);
        }
    }
    @FunctionalInterface
    public interface IFunc3 extends IFunc4 {
        void run(String body, String arg0, String arg1, String arg2);

        @Override
        default void run(String body, String arg0, String arg1, String arg2, String arg3) {
            run(body, arg0, arg1, arg2);
        }
    }
    @FunctionalInterface
    public interface IFunc2 extends IFunc3 {
        void run(String body, String arg0, String arg1);

        @Override
        default void run(String body, String arg0, String arg1, String arg2) {
            run(body, arg0, arg1);
        }
    }
    @FunctionalInterface
    public interface IFunc1 extends IFunc2 {
        void run(String body, String arg0);

        @Override
        default void run(String body, String arg0, String arg1) {
            run(body, arg0);
        }
    }
    @FunctionalInterface
    public interface IFunc0 extends IFunc1 {
        void run(String body);

        @Override
        default void run(String body, String arg0) {
            run(body);
        }
    }

    public static LinkedHashMap<String, IFunc> FUNC_LIST = new LinkedHashMap<>();
    
    static int parseInt(String input, int def) {
        try {
            return Integer.parseInt(input);
        } catch (Exception ignored) {
            return def;
        }
    }

    static {
        FUNC_LIST.put("loop", (IFunc1) (body, arg0) -> {
            int count = parseInt(arg0, 1);
            if (count < 0) count = 0;
            if (count > 255) count = 255;
            
            int finalCount = count;
            TimerTask task = new TimerTask() {
                final AtomicInteger inC = new AtomicInteger(0);
                final AtomicInteger toC = new AtomicInteger(0);
                final int total = finalCount;
                final String[] cmds = body.split(";(\\s)*");
                @Override
                public void run() {
                    if (cmds.length == 0)
                        cancel();
                    if (!ClientCmdHacker.isRunning())
                        cancel();

                    if (!cmds[inC.get()].isEmpty()) {
                        Minecraft mc = Minecraft.getMinecraft();
                        mc.addScheduledTask(() -> {
                            final int c = toC.get(), i = inC.get();
                            mc.player.sendMessage(
                                    new TextComponentString(TextFormatting.DARK_GRAY +
                                            String.format("Run %d:%d: %s", c, i, cmds[i]))
                            );
                            ModCmdHandler.INSTANCE.execute(cmds[i]);
                        });
                    }

                    inC.incrementAndGet();
                    if (inC.get() >= cmds.length) {
                        inC.set(0);

                        toC.incrementAndGet();
                        if (toC.get() >= total)
                            cancel();
                    }
                }
            };
            ClientCmdHacker.setRunning(true);
            ClientCmdHacker.FUNC_TIMER.schedule(task, 1000, 1000);
        });
        FUNC_LIST.put("halt", (IFunc0) (body) -> {
            ClientCmdHacker.setRunning(false);
        });
    }

    public static Timer FUNC_TIMER = new Timer();
    
    static boolean RUNNING = false;
    public static boolean isRunning() { return RUNNING; }
    public static void setRunning(boolean val) { RUNNING = val; }
    
    public static void replaceHandlerInstance() {
        try {
            Field field = ClientCommandHandler.class.getField("instance");
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, ModCmdHandler.INSTANCE);
            MCGProject.logger.info("[MCH] ClientCommandHandler has replaced!");
        } catch (Exception ex) {
            MCGProject.logger.info(ex.toString());
        }
    }

    public static class ModCmdHandler extends ClientCommandHandler {

        public static ModCmdHandler INSTANCE = new ModCmdHandler();

        @Override
        public int executeCommand(ICommandSender sender, String message) {
            if (GuiFilteredChat.INSTANCE.canFilter) {
                Func func = Func.parseFunc(message);
                if (func != Func.EMPTY) {
                    IFunc function = FUNC_LIST.get(func.name);
                    if (function != null)
                        function.run(
                                func.body,
                                func.args[0], func.args[1], func.args[2], func.args[3],
                                func.args[4], func.args[5], func.args[6], func.args[7]
                        );
                    return 1;
                }
            }

            return super.executeCommand(sender, message);
        }

        public ICommand getClientCmd(String message) {
            message = message.trim();
            boolean usedSlash = message.startsWith("/");
            if (usedSlash) {
                message = message.substring(1);
            }

            String[] temp = message.split(" ");
            String[] args = new String[temp.length - 1];
            String commandName = temp[0];
            System.arraycopy(temp, 1, args, 0, args.length);
            return this.getCommands().get(commandName);
        }

        public void execute(String cmd) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            int result = -1;
            if (getClientCmd(cmd) != null)
                result = super.executeCommand(player, cmd);
            if (result != 1)
                player.sendChatMessage(cmd);
        }

    }

}
