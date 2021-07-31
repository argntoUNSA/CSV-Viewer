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
  CSVViewer es un software gratuito y de código abierto para Windows que le permite ver archivos CSV. Desarrollado en Java.
 </p>

# Descripcion general
<p> Consiste en 3 instancias representadas por 3 ventanas que están 
interconectadas, luego las instancias servirán como guía para usar el programa:
    <ol>
      <li>
        <p>Instancia de selección del archivo de tipo CSV.
          </br>
          Esta es la primera instancia, comienza cuando ejecuta el programa (CSVViewer.jar). 
          </br>
          Nos permite elegir un archivo, este debe ser del tipo CSV.
          </br>
          <img src="https://i.postimg.cc/8kZZZLLK/Inicio.png" alt="Open-Start Window">
        </p>
      <li>
        <p>Instancia de preconfiguración de la visualización en formato tabla.
          </br>
          Esta es la segunda instancia, comienza cuando seleccionas y abres un archivo de tipo CSV, para preconfigurarlo y verlo como una tabla.
	  </br>
          <img src="https://i.postimg.cc/xjk30hwQ/inicio.png" alt="Preset-start window">
        </p>
      <li>
        <p>
          Muestra la instancia del archivo CSV en el formato seleccionado.
          </br>
          Esta es la segunda o tercera instancia según el formato elegido en la primera instancia. Esta instancia nos permite ver el archivo CSV en 2 formatos:
	  </br>
          <ul>
            <li>
              <p>
                Visualización del archivo "demo.csv" en formato de tabla.
                </br>
                <img src="https://i.postimg.cc/d0k8jS8v/inicio-Tabla.png" alt="View Window-Table">
              </p>
            <li>
              <p>
                Visualización del archivo "demo.csv" en formato JSON.
                </br>
                <a href='https://postimages.org/' target='_blank'>
                  <img src='https://i.postimg.cc/rpwqBvhX/inicio-JSON.png' border='0' alt='View Window-JSON'/>
                </a>
              </p>
          </ul>
        </p>
    </ol>
</p>

# Requerimientos minimos

<ol>
	<li>
		<p>
			Sistema operativo Windows 8 32/64 bits
		</p>
  <li>
  <p>
	Java SE (JRE) instalado y actualizado. Si no lo tiene o desea actualizarlo, siga <a href="https://www.java.com">este enlace</a>
  </p>

  <li>
  <p>
	Java SE como programa predeterminado para abrir un archivo.
  </p>
</ol>


# Instalacion

<p>
	La instalación es compatible con todas las versiones de Windows desde 2006, no con el programa "CSVViewer 1.5". No obstante, si deseas probarlo, puedes informarnos de tu éxito o fracaso, a partir de ahora estaremos agradecidos por tu contribución.
</p>
	
<ol>
  <li>
	<p>
		Descargue el archivo EXE llamado "Installer-Windows".
		</br>
		Para esto, debe ir al <a href="https://github.com/argntoUNSA/CSV-Viewer">repositorio del proyecto</a> en GitHub y hacer clic en el archivo previamente nombrado. 
		</br>
		<img src="https://i.postimg.cc/1z2Hq6Hj/Seleccionar-Instalador.png" alt="Clicking Installer-Windows.exe">
		</br>
		Luego proceda a descargarlo presionando el botón indicado.
		</br>
		<img src="https://i.postimg.cc/FzQVP4wb/Descargar-Instalador.png" alt="Downloading Installer-Windows.exe">
		</br>
	</p>
  <li>
	<ul>
    <li>
      <p>
      Después de localizar la carpeta de descarga
        </br>
        <a href="https://postimages.org/" target="_blank"><img src="https://i.postimg.cc/NFymZPxb/Abrir-Carpeta-Descargada.png" alt="Localized download folder"/></a>
        </br></br>
      </p>
    <li>
      <p>
        Ejecute el instalador descargado. y será guiado por el asistente de instalación.
        <a href="https://postimages.org/" target="_blank"><img src="https://i.postimg.cc/fy6msj2s/Abrir-Archivo-descargado.png" alt="Runned installer"/></a>
        </br>
      </p>
  </ul>
    

	
</ol>


# Historia

## Resumen
<p>
  Este software es una contribución a un proyecto existente, proviene del usuario <a href="https://github.com/pawarashish564"> pawarashish564 </a>. El cual trabajo para desarrollar un visor de archivos CSV que se pueda manejar por consola.
  Nuestra contribución se centró en refactorizar el código existente facilitando así el desarrollo de una GUI. Este proyecto, entonces, fue impulsado gracias a la posibilidad de trabajar con el código fuente que desarrolló el usuario original, no hace falta decir que las ventajas de trabajar con software libre y de código 
abierto también fueron las que lo permitieron.  </br>
  Para más información, visite este <a href="https://github.com/pawarashish564/CSV-Viewer/network">enlace</a>
</p>

## Cronologia
<ul>
  <li>
    <p>
      01/07/2021 - Creación de la GUI y la documentación.
      </br>
      Aplicamos la primera modificación con respecto al uso del programa, creamos la GUI. Esto llevó a la creación de nuevas clases, por lo que también se amplió la documentación del proyecto.
    </p>
  <li> 
    <p>
      18/06/2021 - Primera vez que hicimos una contribución. Refactorización.
      </br>
      Creamos el "fork" correspondiente para poder aportar al proyecto y realizamos el primer commit, que se centró en organizar el código original del repositorio, manteniendo todo lo relacionado con la funcionalidad y uso del software.
    </p>
  <li>
    <p>
      11/08/2020 - Última actualización del proyecto por pawarashish564.
      </br>
      En estas actualizaciones, el usuario realizó 7 confirmaciones el mismo día. En el que corrigió errores, añadió características y una pequeña documentación del proyecto.
    </p>
  <li> 
    <p> 
      07/08/2020 - Creación del proyecto original:
      </br>
      El usuario pawarashish564 crea el proyecto y comienza a realizar cambios en él. Desconocemos la motivación original del proyecto, pero se centra en la gestión del mismo a través de la consola.
    </p>
</ul>


# Caracteristicas adicionales
- [ ] Jerarquía, cuando hay más de un "preset".
- [ ] Opciones configuradas sin soporte en JSON.

