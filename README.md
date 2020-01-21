DURIANCHASE, game by Elaine Pranadjaya & Nithya Chandran.

HOW IT WORKS
	Our game, DurianChase, has a main character that is movable with courser keys.
	The moving entities in the game are the main character, the helpers, and the bear.
	The entities that don’t move are the basket, trees, and fruit that spawn randomly.

The objective of DurianChase is to collect as many durian fruits, all while avoiding getting eaten by the bear.

You, the main character, will move to collect a durian when you approach.
The counter on the bottom left of the screen increments to show the current amount of fruits you collected in the game.

Mouse-clicks on a vacant tile creates a new helper entity to help you collect fruits.
Additionally, leaves will appear around the area where the mouse clicked which act as obstacles.
The existing bear entity will move a little bit faster every time a helper shows up.

A maximum of 10 fruits can be shown on the screen at once, and fruits are randomly spawned every few moments.
Again, the goal of this game is to collect as many durians as you can. If the bear gets to you, it’s game over and there will be text that shows the final amount of fruits you collected.


DESIGN
We used Singleton for the main character since there can only be one instance of the main character.
We also used implemented the Factory Design method by creating an EntityFactory class to simplify the creation of all of the entities.

The Bear moves using a new pathing algorithm that chooses the best path using the shortest distance of the entity to target.
The Helpers move using the A* pathing strategy.
