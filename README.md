# FACT-Finder Web Components Documentation Exporter

Generates .csv files search

## Dev

### Setup

##### Prerequisites

* Install [Maven](https://maven.apache.org/) 
* Install JDK 8 
* Directory containing markdown files `$MARKDOWN`

##### Run exporter

In the project root directory run:

    mvn exec:java -Dexec.args=$MARKDOWN

### Build / Package

In the project root directory run: 

	mvn clean compile package
	
## Use

### Running exporter

Run the executable jar like this:

	java -jar documentation-export.jar $MARKDOWN
	
