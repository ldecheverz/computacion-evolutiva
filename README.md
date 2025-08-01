# TSP Solver - Ejecución

Este programa resuelve instancias del Problema del Viajante (TSP) usando un algoritmo evolutivo configurable.

## 📦 Ejecución

```bash
java -jar tsp-solver.jar <file> <selector> <mutator> <crossover> <initiator> <populationSize> <generationSize> <mutationRate> <crossoverRate>

<file>: nombre del archivo .atsp con matriz de costos.

<selector> puede ser:
  - tournament
  - ranking

<mutator> puede ser:
  - swap
  - invert
  
<crossover> puede ser:
  - order       (Order Crossover - OX)
  - pmx         (Partially Mapped Crossover)

<initiator> puede ser:
  - random
  - heuristic

<populationSize>: tamaño de la población, entero (ej: 300)

<generationSize>: número de generaciones, entero (ej: 1000)

<mutationRate>: probabilidad de mutación, decimal entre 0.0 y 1.0 (ej: 0.15)

<crossoverRate>: probabilidad de cruce, decimal entre 0.0 y 1.0 (ej: 0.6)

Ejemplo:
java -jar tsp-solver.jar p43.atsp tournament swap order random 300 1000 0.15 0.6