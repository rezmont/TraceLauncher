# TraceLauncher

__Looking Glass automation__: 
This code is developed as part __IMPACT__ project on mapping AS-interconnections in different cities. More information about the general context of the project at http://onrg.cs.uoregon.edu/impact/

The specific task at hand is as follows. We mainly rely of `traceroute` measurements to recover the Internet topology. We use Looking Glass (LG) servers in order to conduct measurements from other locations. Each LG is (web-based) front-end that interfaces with a list of routers in back-end, and allows users to conduct network measurements (traceroute, ping, etc). An example of such a LG server at `http://www.cogentco.com/en/network/looking-glass` a simple Google search would yield many more. 

We primarily use LGs to conduct traceroute and obtain BGP connectivity (AS paths). To automate the measurement (so that we do not need a person to manually go to a web site and start a measurement) we developed this code to achieve this goal. We have automated data collection from many LGs but there are still more LGs that we need to add our list.