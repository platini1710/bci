 1.- Para bajar el proyecto, copie el siguiente comando en la linea de comando:
 
      git clone --branch master https://github.com/platini1710/globalLogic.git
	  
2.-	Para probar los test unitario y de integracion debe colocar el siguiente comando

      ./gradlew check
	  
	  los reporte html  estan en la carpeta 
	  
	  
	test de integracion ==>  tareasBci\build\reports\tests\integrationTest\index.html
	test unitarios ==>  tareasBci\build\reports\tests\test\index.html
	
	 
	 
3.-	Para Levanar   proyecto el :
	  
      ./gradlew run               ==> ojo, el proyecto se levantara en un tomcat emebedido en el puerto 8081
	   
	   
4.-	para probar con datos en el Postman
      - ingresar un registro de un usuario, se debe ingresar en formato Json
			url   :  http://localhost:8081/registro/usuario/sign-up
			Metodo:  Post
			
			{
			"id" :"1-8",    //rut chileno sin validar
			"name": "augusto Espinoza",
			"password": "A23aaaaa#@",
			"email": "aerrspinoza3010@gmail.com",
			"phone": {
						"number":12212221

					}
			}
		

      - consultar por el registro  ingresado
			url   :  http://localhost:8081/consult/usuario/login/1-8
			Metodo:  Get	  
			Primero ingresar  en la pestaña Header y agregar la key Authorization y el valor del token que muestra cuando se crea el registo 
			luego ingrear el Json  
			{
			  "id" :"11566187-6"
			}
		
		
		
		
	  - consultar por todos los registros  ingresados
	  	    url   :  http://localhost:8081/consult/usuario/allUsuarios
		    Metodo:  Get	
	        Primero ingresar  en la pestaña Header y agregar la key Authorization y el valor del token que muestra cuando se crea el registo 
		     no se ingresa nada, se deja vacio
			 
			 
			 
			 
			 
			 DIAGRAMA DE SECUENCIA y de componete dentro de LA CARPETA diagramas