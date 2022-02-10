# Cooperative-RL-methods-for-Hunting-game
This project is an improved version of Simbad Simulator (http://simbad.sourceforge.net/).  It consists of a cooperative hunting task with a temporary moving target. It is a popular case study where some agents, called predators, are trying to capture one other agent, called the prey.

A new discrete environment is implemented here with several new distributed and cooperative multi-agent reinforcement learning methods. We principally propose:
*	Two new action selection strategies: The TM maxNextQ Policy and The the TM maxNextQ LRVS Policy.
*	A new distributed reinforcement learning approach for two agent systems. 

<img src="/Cooperative-RL-methods-for-Hunting-game/images/Testing_environment.png" alt="Testing_environment"/>

This project is the codebase for the paper "Cooperative Multi-Agent Systems Using Distributed Reinforcement Learning Techniques" in KES 2018, located at https://www.sciencedirect.com/science/article/pii/S1877050918312626. The usage code below makes sure you can reproduce almost same results as shown in the paper.

In this paper, the fully cooperative multi-agent system is studied, in which all of the agents share the same common goal. The main difficulty in such systems is the coordination problem: how to ensure that the individual decisions of the agents lead to jointly optimal decisions for the group? Firstly, a multi-

<img src="/Cooperative-RL-methods-for-Hunting-game/images/learningIteration.png" alt="learningIteration"/>

<img src="/Cooperative-RL-methods-for-Hunting-game/images/collision.png" alt="collision"/>

<img src="/Cooperative-RL-methods-for-Hunting-game/images/collisionAfterConvergence.png" alt="collisionAfterConvergence"/>
