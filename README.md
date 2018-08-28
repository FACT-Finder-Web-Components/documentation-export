# FACT-Finder Web Components Documentation Exporter

Generates .csv files search

### Build / Package

In the project root directory run: 

	mvn clean compile package

### Running exporter

Run the executable jar like this:

	java -jar documentation-export.jar -a path/to/api-files/ -d path/to/documentation-files/ 
	
### Options

#### -u / --url

Base URL that is prepended to the relative url

#### -o / --output-dir

Export files are placed in this directory
	
