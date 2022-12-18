package barabanov.ui.console;


import java.util.Arrays;

public class Checker
{

    static boolean checkPlayerArgToCreation(String[] args) { return args.length == 2 && isNumeric(args[0]); }

    static boolean checkItemArgToCreation(String[] args) { return args.length == 4 &&
            Arrays.stream(args).allMatch(Checker::isNumeric); }

    static boolean checkCurrencyArgToCreation(String[] args) { return args.length == 4 && isNumeric(args[0]) &&
            isNumeric(args[1]) && isNumeric(args[3]); }

    static boolean checkProgressArgToCreation(String[] args) { return args.length == 4 &&
            Arrays.stream(args).allMatch(Checker::isNumeric); }

    static String checkCmdSyntax(String[] commandWords)
    {

        if (commandWords.length < 2)
            return "Неопознанная команда: " + String.join(" ", commandWords);

        if ( !(commandWords[0].equals("create") || commandWords[0].equals("read") || commandWords[0].equals("update")
                || commandWords[0].equals("delete")))
            return "Команда \"" + commandWords[0] + "\" не поддерживается.";

        if (commandWords[0].equals("update") && !commandWords[1].equals("player"))
            return "Команда \"update\" не поддерживается для " + commandWords[1];

        if (commandWords[1].equals("player"))
        {
            if ( !commandWords[0].equals("create"))
            {
                if (commandWords.length < 4)
                    return "Неверно заданы параметры команды " + commandWords[0];

                if ( !commandWords[2].equals("playerId="))
                    return "Идентификатор \"playerID\" не распознан.";

                if ( !isNumeric(commandWords[3]))
                    return "Неверно задан playerId " + commandWords[3] + ". playerId должен являться числом.";

                if (commandWords[0].equals("update"))
                {
                    if (commandWords.length != 6)
                        return "Неверно указаны параметры команды \"update player\"";

                    if ( !commandWords[4].equals("nickname="))
                        return "Идентификатор \"nickname\" не распознан.";
                }
            }
            else
            if (commandWords.length != 2)
                return "Неверно вызвана команды \"create player\"";

        } else if (commandWords[1].equals("currency") || commandWords[1].equals("item") || commandWords[1].equals("progress"))
        {
            if (commandWords[0].equals("read") || commandWords[0].equals("delete"))
            {
                if (commandWords.length != 6)
                    return "Неверно указаны параметры команды " + commandWords[0];

                if ( !commandWords[2].equals("id="))
                    return "Идентификатор \"id\" не распознан.";

                if ( !isNumeric(commandWords[3]))
                    return "Неверно указан id команды " + commandWords[0];

                if ( !commandWords[4].equals("playerId="))
                    return "Идентификатор \"playerId\" не распознан.";

                if ( !isNumeric(commandWords[5]))
                    return "Неверно указан playerId команды " + commandWords[0];
            }

            else
            {
                if (!commandWords[2].equals("for") || !commandWords[3].equals("player"))
                    return "Неопознанная команда: " + String.join(" ", commandWords);

                if ( !commandWords[4].equals("playerId="))
                    return "Идентификатор \"playerID\" не распознан.";

                if ( !isNumeric(commandWords[5]))
                    return "Неверно задан playerId " + commandWords[5] + ". playerId должен являться числом.";
            }
        }
        else
            return "Неопознанная команда: " + String.join(" ", commandWords);

        return "checked";
    }


    static boolean isNumeric(String s)
    {
        try
        {
            Long.parseLong(s);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
