package util;

public class Manager {
    private final CollectionManager collectionManager = new CollectionManager();
    private final CommandManager commandManager = new CommandManager();
    private final AuthorizerManager authorizerManager = new AuthorizerManager();

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public AuthorizerManager getAuthorizerManager() {
        return  authorizerManager;
    }


}
