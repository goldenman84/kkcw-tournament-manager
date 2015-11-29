# Einleitung #

Diese Seite beinhaltet die technischen Grundlagen der Applikation


## Wt: C++ Web Toolkit ##

Die Wt Bibliothek kann auf allen gängigen Betriebssystemen (Linux, MacOSX und Windows) installiert werden. In unserem Projekt werden wir uns nur auf den Linux-Distribution **Ubuntu** beschränken.

## Installation ##

### Ubuntu ###
#### Installation ####
```xml

$ sudo add-apt-repository ppa:pgquiles/wt
$ sudo apt-get update
$ sudo apt-get install witty witty-dev witty-doc witty-examples
```
#### Ausführen der Beispiele ####
Die Beispieldateien befinden sich bei der Standardinstallation unter `usr/lib/Wt/examples` und können wie folgt ausgeführt werden. Unten wird das 'hello' Beispiel ausgeführt.

```xml

$ /usr/lib/Wt/examples/hello/hello
```

Die Konsole sollte die folgenden Ausgaben enthalten:

```xml

[ catilgan@catilgan-hp ]/usr/lib/Wt/examples/hello/hello
Reading: /etc/wt/wt_config.xml
[2011-Nov-07 12:48:57.775531] 5699 - [notice] "Wt: initializing built-in httpd"
[2011-Nov-07 12:48:57.775632] 5699 - [notice] "Reading Wt config file: /etc/wt/wt_config.xml (location = '/usr/lib/Wt/examples/hello/hello.wt')"
[2011-Nov-07 12:48:57.779481] 5699 - [notice] "Started server: http://0.0.0.0:8080"
```

Der lokal gestartete Server auf dem die Applikation läuft ist unter **`http://0.0.0.0:8080`** erreichbar.

## Kompilieren und Ausführen der Applikation ##

Die Ordnerstrukturen der Applikation sehen momentan wie folgt aus:
<pre>
├── build<br>
├── src<br>
│   ├── controller<br>
│   ├── model<br>
│   ├── resources<br>
│   └── view<br>
└── target<br>
<br>
</pre>

Im **build** Ordner befindet sich das Konfigurationsdatei für das **cmake** Tool. Diese Tool erzeugt das Makefile Datei für das Kompilieren der Applikation.

Um das Makefile zu erzeugen muss die folgende Befehle ausgeführt werden:

```xml

$ cd build
$ cmake .
```

Nach der erfolgreichen Ausführung des obigen Befehls kann nun der Quellcode der Applikation kompiliert werden. Dieser Befehl erzeugt ein ausführbares Datei im **target** Ordner.

```xml

$ make
```

Im **target** Ordner sollte nun das ausführbare Datei **controller.wt** abgelegt sein. Mit den folgenden Befehlen kann der lokale Server gestartet werden, welche durch die Adress **`http://0.0.0.0:8080`** erreichbar ist.

<pre>
target/<br>
└── controller.wt<br>
</pre>
```xml

$ cd ../target
$ ./controller.wt --docroot . --http-address 0.0.0.0 --http-port 8080
```


## Notwendige Tools ##
### cmake ###
```xml

$ sudo aptitude install cmake
```

### make ###
```xml

$ sudo aptitude install make
```

## Wt:Dbo MySQL backend ##
### SVN checkout ###
svn co https://wtdbomysql.svn.sourceforge.net/svnroot/wtdbomysql wtdbomysql

### Installation ###
```xml

$ sudo aptitude install  libmysqlclient-dev
$ sudo aptitude install doxygen
$ sudo aptitude install  libboost-test-dev
$ cd wtdbomysql/trunk
$ cmake .
$ make
$ sudo make install
$ sudo ln -s /usr/local/lib/libwtdbomysql.so.23 /usr/lib/libwtdbomysql.so.23

```

## ANSI Quoting in MySQL ##

In MySQL muss das globale _sql mode_ Parameter wie folgt gesetzt werden, damit die SQL Statements in Wt::Dbo mit Double Quotes funktionieren:
```xml

$ mysql -uroot
mysql> SET  @@GLOBAL.sql_mode = 'ANSI_QUOTES';
```

### Wichtige Links ###
  * **Wt**: C++ Web Toolkit ([Hauptseite](http://www.webtoolkit.eu/wt))
  * **Offizielle Wt Dokumentation**: ([Dokumentationsseite](http://www.webtoolkit.eu/wt/doc/reference/html/index.html))
  * **Wt Redmine**: Die Redmine Projektseite ([Redmine](http://redmine.webtoolkit.eu/projects/wt/wiki))
  * **CMake**: the cross-platform, open-source build system ([Hauptseite](http://www.cmake.org/))