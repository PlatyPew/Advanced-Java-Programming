

# ST0316 Advanced java programming

**Simple TOURIST TRANSPORT GUIDE APPLICATION**

### The Problem

In computer science, a **hash table** or **hash map** is a data structure that uses a hash function to efficiently map certain identifiers or keys (e.g., person names) to associated values (e.g., their telephone numbers). The hash function is used to transform the key into the index (the _hash_) of an array element (the _slot_ or _bucket_) where the corresponding value is to be sought.

The hashtable or hash map data structure typically improves performance of application performance when large amount of data is required to be processed.

In this assignment, you are required to implement a simple public transport guide application for a tourist visiting Singapore. The app is able to perform basic tasks like finding the transport services to take from a boarding stop/station to an alighting stop/station, or finding the full route of a transport service. You are to program this application taking efficiency into account as though large amount of data and processing is involved (Use hashmaps to store the read in data). The data files are from Land Transport Authority and provided/licensed by [www.dex.sg](http://www.dex.sg/).  Refer to LTA website for the complete MRT system map: [http://goo.gl/yQ6ciN](http://goo.gl/yQ6ciN).

Functionalities that you need to include would be as follows:

#### Basic Features (MRT Routes)

1. You are given some input text files. Load the text document information from the given text files.

2. One such file is MRT.txt. Study the contents and add in the missing MRT train lines and stations for North East Line based on the LTA map. You should follow the existing file format. You **do not need** to add in Thomson East Coast line and the LRT lines. The file is in this format, whereby station numbers are in order:

```
(start)
Line 1 station number 1
Line 1 station Description 1
…
Line 1 Station number n
Line 1 station Description n
(end)
```

1. For each transport service, you may like to create an appropriate class like a Service class. Likewise for transport stops/stations and routes.

2. Store the data into one or more hashmap. You are recommended to use 2 hashmaps: The first 2 hashmaps will use one to store the line number as key, and another one to store the full station description as key.

3. Provide a menu for the tourist to enter queries. Users can search by station number (eg EW5), and program will show the route of the line.

4. User can also key in the boarding station, and alighting station (either station number or station name), and program is supposed to find
	1. The direct line which services these 2 points.
		2. Train services which require 1 transfer.

		For simplicity, you are only required to show any 1 route for basic functionality.

#### Advanced Features (Bus Routes to Tourist Attractions)

1. There are other files provided. The text file (lta-bus\_stop\_codes.csv) is in this format (delimiter is comma):

`BUSSTOP_CODE,ROAD_DESCRIPTION,BUSSTOP_DESCRIPTION`

2. The last file (lta-sbst\_route.csv) is in this format:

`SVC_NUM,DIR,ROUTE_SEQ,BUSSTOP_CODE,DISTANCE`

3. The tourist may also switch to buses to reach their ultimate destination. Hence they can also key in the boarding bus stop, and alighting bus stop, and program is supposed to find

	1. The direct service which services these 2 points.
		2. Bus services which require 1 transfer.

### Extra Features
You are encouraged to provide extra advanced features for your application. This may include functionalities to improve the efficiency of the application or enhance the functionality of the application. Examples would be:

1. Showing the number of stations from start to destination and intersection station.
2. Showing the number of stations required for each service and then sorting by the best route. The best route in this case, would be the service that requires the least number of stations. Display the matching train services in ascending order of the number of stations. You are required to make use of the Comparable interface to help you do sorting.
3. Show the matching station description based on substring search, to help user in providing the correct station name.
4. Show all possible routes from start to destination.
5. Handle train disruptions or faults.
6. Handle multiple transfers: between lines and/or bus services.
7. Showing the number of stops from start to destination and intersection stops.
8. Showing the number of stops required for each service and then sorting by the best route. The best route in this case, would be the service that requires the least number of stops or distance. Display the matching services in ascending order. You are required to make use of the Comparable interface to help you do sorting.
9. Show the matching stop description based on substring search, to help user in providing the correct stop name.
10. Show all possible routes from start to destination.
11. Display service routes on a map.
