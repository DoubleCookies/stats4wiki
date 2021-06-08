# Stats4wiki

[![Build Status](https://travis-ci.org/DoubleCookies/stats4wiki.svg?branch=master)](https://travis-ci.org/DoubleCookies/stats4wiki)

Statistics tool for building tables and templates for GDWiki top levels lists pages. 
Based on [GdStatistics](https://github.com/DoubleCookies/GDStatistics) project, 
which is based on Alex1304 [ultimategdbot](https://github.com/alex1304/ultimategdbot) *(we need to go deeper)*. 

## Features
- Collect information about top-50 most downloaded/liked levels (all and demons difficulties) and generate table for it.
- Generate short lists for wiki templates.
Statistics tables contains ru-headers and templates but it's easy to change it if you need! 

## Structure
- **Statistics** folder contains markdown lists with statistics information.
- **src** folder contains Java sources for collecting, processing and saving data.
- **web** folder contains JS sources for **[Github page](https://doublecookies.github.io/stats4wiki/)** with info about project.

## Launch
- For web-part you can check README in **web** folder.
- For java-part the easiest way - launch project via the IDE.

## License
This project has [MIT License](https://opensource.org/licenses/MIT)