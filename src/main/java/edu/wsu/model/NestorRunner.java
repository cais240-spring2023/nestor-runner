package edu.wsu.model;

public interface NestorRunner {

  /**
   * Get the winner of the game, will return null if the game is not over.
   * @return The name of the winner
   */
  String getWinner();

}