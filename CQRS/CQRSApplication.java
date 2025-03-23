public class CQRSApplication {
  public static void main(String[] args) {
      UserRepository userRepository = new UserRepositoryImpl();
      CreateUserCommandHandler createHandler = new CreateUserCommandHandler(userRepository);
      UpdateUserEmailCommandHandler updateEmailHandler = new UpdateUserEmailCommandHandler(userRepository);
      GetUserProfileQueryHandler queryHandler = new GetUserProfileQueryHandler(userRepository);
      ReadUserEmailQueryHandler readEmailHandler = new ReadUserEmailQueryHandler(userRepository);

      CommandBus commandBus = new SimpleCommandBus(createHandler, updateEmailHandler);
      QueryBus queryBus = new SimpleQueryBus(queryHandler, readEmailHandler);

      // Create User
      CreateUserCommand createCommand = new CreateUserCommand("1", "Alice", "alice@example.com");
      commandBus.dispatch(createCommand);

      // Update Email
      UpdateUserEmailCommand updateEmailCommand = new UpdateUserEmailCommand("1", "alice_new@example.com");
      commandBus.dispatch(updateEmailCommand);

      // Read Updated Email
      ReadUserEmailQuery readEmailQuery = new ReadUserEmailQuery("1");
      String updatedEmail = queryBus.dispatch(readEmailQuery);
      System.out.println("Updated Email: " + updatedEmail);

      // Read User Profile
      GetUserProfileQuery profileQuery = new GetUserProfileQuery("1");
      UserProfileDTO userProfile = queryBus.dispatch(profileQuery);
      System.out.println(userProfile);
  }
}