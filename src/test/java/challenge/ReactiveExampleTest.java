package challenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;


@ExtendWith(MockitoExtension.class)
class ReactiveExampleTest {

    @InjectMocks
    ReactiveExample reactiveExample;

    @Test
    void sumaDePuntajes() {
        //arrange
        StepVerifier
                .create(reactiveExample.sumaDePuntajes()) //act
                .expectNext(260)//asert
                .verifyComplete();

        Optional.ofNullable(reactiveExample.sumaDePuntajes().block())
                .filter(Objects::nonNull)
                .ifPresentOrElse(puntaje -> {
                            Assertions.assertEquals(260, puntaje);
                        },
                        Assertions::fail
                );
    }

    @Test
    void mayorPuntajeDeEstudiante() {
        //arrange
        var estudiante = reactiveExample.mayorPuntajeDeEstudiante(2); //act

        StepVerifier.create(estudiante)
                .expectNextMatches(validarEstudiante("pedro", 80, 5))//assert
                .expectNextMatches(validarEstudiante("juan", 75, 5))
                .expectComplete()
                .verify();
    }

    private Predicate<Estudiante> validarEstudiante(String nombre, Integer puntaje, Integer cantidadDeAsistencias) {
        return estudiante -> estudiante.getNombre().equals(nombre)
                && estudiante.getPuntaje().equals(puntaje)
                && estudiante.getAsistencias().size() == cantidadDeAsistencias;
    }

    @Test
    void totalDeAsistenciasDeEstudiantesComMayorPuntajeDe() {
        //arrange
        var totalAsistencias = reactiveExample.totalDeAsistenciasDeEstudiantesConMayorPuntajeDe(74);//act

        StepVerifier.create(totalAsistencias)
                .expectNext(43)//assert
                .expectComplete()
                .verify();
    }

    @Test
    void elEstudianteTieneAsistenciasCorrectas() {
        //arrange
        var estudiante = new Estudiante("andres", 35, List.of(4, 2, 4, 3, 5));

        StepVerifier.create(reactiveExample.elEstudianteTieneAsistenciasCorrectas(estudiante))//act
                .expectNext(Boolean.TRUE)// assert
                .expectComplete()
                .verify();
    }

    @Test
    void promedioDePuntajesPorEstudiantes() {
        //arrange
        var puntaje = 52.0;

        StepVerifier.create(reactiveExample.promedioDePuntajesPorEstudiantes())//act
                .expectNext(puntaje)//assert
                .verifyComplete();
    }

    @Test
    void estudiantesAprobados() {
        //arrange
        StepVerifier.create(reactiveExample.estudiantesAprobados())//act
                .expectNext("juan")
                .expectNext("pedro")//assert
                .expectComplete()
                .verify();
    }

    @Test
    void losNombresDeEstudianteConPuntajeMayorA() {
        StepVerifier
                .create(reactiveExample.losNombresDeEstudianteConPuntajeMayorA(74))
                .expectNext("juan", "pedro")
                .expectComplete()
                .verify();
    }
}