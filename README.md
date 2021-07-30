<h1 align="center"> CSV-Viewer </h1>
<p align="center">
<img src="logo.svg">
</p>
<p align="center">
<img src='https://img.shields.io/badge/made%20with%20%E2%9D%A4%EF%B8%8F%20-java%20-orange'>
<img src="https://badgen.net/badge/Open%20Source%20%3F/Yes%21/blue?icon=github">
<img src="https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat">
<img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square">
<a href="https://gitmoji.carloscuesta.me">
  <img src="https://img.shields.io/badge/gitmoji-%20??%20??-FFDD67.svg?style=flat-square" alt="Gitmoji">
</a>
</p>
<!-- <p align="center"><h1>CSVViewer</h1></p> -->

<p>
  CSVViewer is free and open source software that allows you to view CSV files. Developed in Java.
 </p>

# General description
<p>
  It consists of 3 instances represented by 3 windows which are interconnected, then instances will serve as a guide for using the program:
    <ol>
      <li>
        <p>Selection instance of the CSV type file.
          </br>
          This is the first instance, it starts when you run the program (CSVViewer.jar). 
          </br>
          It allows us to choose a file, this must be of the CSV type. 
          </br>
          <img src="https://i.postimg.cc/8kZZZLLK/Inicio.png" alt="Open-Start Window">
        </p>
      <li>
        <p>
          Instance of preconfiguration of the visualization in table format.
          </br>
          This is the second instance, it starts when you select and open a file of the CSV type, to preconfigure it and view it as a table.
          </br>
          <img src="https://i.postimg.cc/xjk30hwQ/inicio.png" alt="Preset-start window">
        </p>
      <li>
        <p>
          Display instance of the CSV file in the selected format.
          </br>
          This is the second or third instance according to the format chosen in the first instance. This instance allows us to view the CSV file in 2 formats:
          </br>
          <ul>
            <li>
              <p>
                Visualization of the file "demo.csv" in table format.
                </br>
                <img src="https://i.postimg.cc/d0k8jS8v/inicio-Tabla.png" alt="View Window-Table">
              </p>
            <li>
              <p>
                Visualization of the file "demo.csv" in JSON format
                </br>
                <a href='https://postimages.org/' target='_blank'>
                  <img src='https://i.postimg.cc/rpwqBvhX/inicio-JSON.png' border='0' alt='View Window-JSON'/>
                </a>
              </p>
          </ul>
        </p>
    </ol>
</p>

# Minimum requirements

<ol>
  <li>
  <p>
	Java SE (JRE) installed and updated. If you do not have it or want to update it, follow <a href="https://www.java.com">this link</a>
  </p>

  <li>
  <p>
	Java SE as the default program to open a JAR file.
  </p>
</ol>


# Install

## Windows operating system
<ol>
  <li>
	<p>
		Download the EXE file called "Installer-Windows".
		</br>
		For this, you should go to the <a href="https://github.com/argntoUNSA/CSV-Viewer">project's repository</a> on GitHub and click on the previously named file.
		</br>
		<img src="https://i.postimg.cc/1z2Hq6Hj/Seleccionar-Instalador.png" alt="Clicking Installer-Windows.exe">
		</br>
		Then proceed to download it by pressing the indicated button
		</br>
		<img src="https://i.postimg.cc/FzQVP4wb/Descargar-Instalador.png" alt="Downloading Installer-Windows.exe">
		</br>
	</p>
  <li>
	<ul>
    <li>
      <p>
        After locating the download folder
        </br>
        <a href="https://postimages.org/" target="_blank"><img src="https://i.postimg.cc/NFymZPxb/Abrir-Carpeta-Descargada.png" alt="Localized download folder"/></a>
        </br></br>
      </p>
    <li>
      <p>
        Run the downloaded installer. and you will be guided by the installation wizard.
        <a href="https://postimages.org/" target="_blank"><img src="https://i.postimg.cc/fy6msj2s/Abrir-Archivo-descargado.png" alt="Runned installer"/></a>
        </br>
      </p>
  </ul>
    

	
</ol>


# History

## Summary
<p>
  This software is a contribution to an existing project, it comes from the user <a href="https://github.com/pawarashish564"> parawarashish564 </a>. Which I work to develop a CSV file viewer that can be handled by console.
  Our contribution was focused on refactoring the existing code thus facilitating the development of a GUI.
  This project, then, was promoted thanks to the possibility of working with the source code that the original user developed, it goes without saying that the advantages of working with free and open source software were also what allowed this.
  </br>
  For more information, visit this <a href="https://github.com/pawarashish564/CSV-Viewer/network">link</a>
</p>

## Cronology
<ul>
  <li>
    <p>
      01/07/2021 - Creating the GUI and documentation.
      </br>
      We apply the first modification regarding the use of the program, we create the GUI. This led to the creation of new classes, so the documentation of the project was also expanded.
    </p>
  <li> 
    <p>
      18/06/2021 - First time us made a contribution. Refactoring.
      </br>
      We created the corresponding fork to be able to contribute to the project and we made the first commit, which focused on organizing the original repository code, keeping everything related to the functionality and use of the software.
    </p>
  <li>
    <p>
      11/08/2020 - Last update of the project by pawarashish564.
      </br>
      In these updates, the user made 7 commits on the same day. In which he corrected errors, added characteristics and a small documentation of the project.
    </p>
  <li> 
    <p> 
      07/08/2020 - Creation of the original project:
      </br>
      User pawarashish564 creates the project and starts making changes to it. We do not know the original motivation of the project, but it focuses on the management through the console.
    </p>
</ul>


# TODO

- [ ] Set multi filters
- [ ] limit option support in JSON
