# Spring Boot : Application PetClinic

## Features  

### 1.  Unit Test  --> Tag v1.0.0

mvn test -Dspring.profiles.active=h2

### 2.  Laboratorio 11 : Pruebas Unitarias con Base de Datos

Ejecutar todas las pruebas desde la consola de Windows (perfil H2):

```
mvn test -Dspring.profiles.active=h2
```

Ejecutar una sola clase de prueba:

```
mvn test -Dtest=VetServiceTest
```

| Caso | Tabla            | Clase de prueba          | Rama Git                    | Pruebas |
|------|------------------|--------------------------|-----------------------------|---------|
| 1    | vets             | `VetServiceTest`         | `feature/vet-tests`         | create, update, findById, findByLastName, findInactive, delete |
| 2    | owners           | `OwnerServiceTest`       | `feature/owner-tests`       | create, update, findById, findByLastName, findByCity, delete |
| 3    | specialties      | `SpecialtyServiceTest`   | `feature/specialty-tests`   | create, update, findById, findByName, findByOffice, delete |
| 4    | visits           | `VisitServiceTest`       | `feature/visit-tests`       | create, update, findById, findByPetId, findByVetId, delete |
| 5    | types            | `PetTypeServiceTest`     | `feature/type-tests`        | create, update, findById, findByName, findInactive, delete |
| 6    | vet_specialties  | `VetSpecialtyServiceTest`| `feature/vet-specialty-tests` | assign, update, findById, findSpecialtiesByVet, findVetsBySpecialty, findSpecialtiesByVet_Empty, remove |

Resultado: **52 pruebas, 0 fallos, 0 errores (BUILD SUCCESS)**.

#### Conclusiones

1. Las pruebas unitarias con base de datos (H2 en memoria con `schema.sql` y `data.sql`) permiten validar el CRUD real de cada servicio sin depender de un servidor externo, por lo que se pueden ejecutar en cualquier equipo con `mvn test`.
2. Las pruebas de creación, actualización y eliminación deben crear sus propios registros para no alterar los datos semilla que usan las pruebas de búsqueda; así las pruebas son independientes del orden de ejecución.
3. Las tablas con llave compuesta como `vet_specialties` requieren una clase de identificador (`@IdClass`) para poder buscarlas y eliminarlas con Spring Data JPA.
4. Trabajar cada caso en su propia rama `feature/...` y fusionarla a `main` con `merge --no-ff` deja un historial claro de lo que aportó cada parte del equipo.
