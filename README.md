# Stats4wiki

[![Build Status](https://travis-ci.org/DoubleCookies/stats4wiki.svg?branch=master)](https://travis-ci.org/DoubleCookies/stats4wiki)

Statistics tool for building tables and templated for GDWiki top levels lists pages. 
Based on [GdStatistics](https://github.com/DoubleCookies/GDStatistics) project, 
which is based on Alex1304 [ultimategdbot](https://github.com/alex1304/ultimategdbot) *(we need to go deeper)*. 

## Features
- Collect information about top-50 most downloaded/liked levels and generate table for it.
- Collect information about top-50 most downloaded demons and generate table for it.
- Generate short lists for wiki templates.
- Transform level names and level authors to links (you can found it in **`Constants.java`**).
Statistics tables contains russian headers and templates but it's easy to change it if you need! 

## Structure
- **Statistics** folder contains markdown lists with statistics information.
- **src** folder contains Java sources for collecting, processing and saving data.
- **dist** folder contains JS sources for Github page.

## License
This project has [MIT License](https://opensource.org/licenses/MIT)