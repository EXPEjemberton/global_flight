## global_flight Hackathon Project

This project concerns itself with the creation of a Google Flights-like service with Expedia's APIs which, because they remained unavailable to us, were mocked internally with a random flight generator.

Tech stack:
- Scala (CRUD endpoints)
- Spray (HTTP Server)
- Neo4j (Graph database)
- CSV files (for unchanging city/airport data in place of an RDBMS)

Contributors include:
- Bhavik Patel (primarily front-end)
- David Kettlestrings (primarily back-end)
- Jonathan Emberton (primarily back-end)

To get started with this project:

1. Launch SBT:

        $ sbt

2. Compile everything and run all tests:

        > test

3. Start the application:

        > re-start

4. Browse to [http://localhost:8080](http://localhost:8080/)

5. Stop the application:

        > re-stop
        
## Endpoints

There are only a few endpoints at the moment, with more coming soon:

1. /isActive

2. /find_nearest_city&latitude={latitude}&longitude={longitude}

    > http://localhost:8080/find_nearest_city?latitude=41.8781136&longitude=-87.6297982

3. /retrieve_map_points&north={northEdge}&south={southEdge]&east={eastEdge]&west={westEdge}

    > http://localhost:8080/retrieve_map_points?north=41.8781136&south=31.98223&east=90.19921&west=-87.6297982