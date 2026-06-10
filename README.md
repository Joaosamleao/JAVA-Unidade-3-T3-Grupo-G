# UVA 820 - Internet Bandwidth

## Sobre o Problema
* **Nome do Problema:** Internet Bandwidth (UVA 820)
* **Link do Problema:** [UVA Online Judge - 820](https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=761)

## Integrantes do Grupo
* João Victor Sampaio
* José Holanda

## Linguagem Utilizada
* **Java** (Versão 8 ou superior recomendada pelo juiz online).

---

## Como Executar a Solução

1. Certifique-se de ter o [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/) instalado na sua máquina.
2. Clone este repositório ou baixe os arquivos fonte.
3. Se estiver utilizando o arquivo de entrada `entrada_do_problema.txt`, certifique-se de que ele está na pasta `dados/` na raiz do projeto.
4. Abra o terminal na pasta onde o arquivo `Main.java` se encontra e compile o código:
   ```bash
   javac Main.java
   ```
5. Execute a classe principal:
   ```bash
   java Main.java
   ```

## Modelagem como Rede de Fluxo
O problema consiste em encontrar a largura de banda máxima que pode ser transmitida entre dois computadores em uma rede. Essa situação é um exemplo clássico do problema de Fluxo Máximo em grafos.

* Definições na RedeOrigem (Source - s): O computador a partir do qual a informação será enviada.
* Sorvedouro (Sink - t): O computador de destino final que receberá a informação.
* Vértices (V): Os computadores (nós) da rede.
* Arestas (E): Os cabos que conectam os computadores.
* Capacidades: A largura de banda máxima suportada por cada cabo individualmente.

## Algoritmo e Estruturas
Utilizamos o algoritmo de Edmonds-Karp, que é uma implementação específica do método de Ford-Fulkerson. A escolha se dá pela utilização de uma Busca em Largura (BFS) para encontrar os caminhos aumentantes, o que garante encontrar os caminhos mais curtos (em número de arestas) primeiro. Isso evita que o algoritmo caia em caminhos ineficientes, garantindo a sua terminação em tempo polinomial.

## O Grafo Residual
O grafo residual é fundamental neste algoritmo. Ele representa as capacidades que ainda estão disponíveis (capacidades residuais) na rede após o envio de uma certa quantidade de fluxo.

* Avanço: Quando enviamos fluxo de u para v, diminuímos a capacidade residual de u -> v.
* Retorno (Undo): Simultaneamente, adicionamos essa mesma quantidade à capacidade residual da aresta reversa v -> u. Isso é crucial, pois permite que o algoritmo "se arrependa" e desfaça decisões anteriores ruins, redirecionando o fluxo por caminhos melhores encontrados posteriormente.

Na nossa implementação, o grafo residual é gerenciado implicitamente através da matriz bidimensional cap[][].

## Análise de Complexidade
* Tempo: A complexidade assintótica do algoritmo de Edmonds-Karp é O(V . E^2), onde V é o número de vértices (computadores) e E é o número de arestas (conexões). Como o problema estabelece um limite pequeno de nós (V <= 100), a abordagem é extremamente rápida e passa confortavelmente nos limites de tempo do juiz.
* Espaço: Utilizamos uma matriz de adjacência 100x100 (int[][] cap), o que requer O(V^2) de complexidade de espaço. O impacto na memória é ínfimo.

## Casos Especiais Relevantes
1. Arestas Múltiplas / Paralelas: Pode haver mais de um cabo conectando os mesmos dois computadores. Modelamos isso não substituindo a capacidade, mas somando os valores na matriz de adjacência: cap[u][v] += bandwidth.
2. Conexões Bidirecionais: Os cabos de internet no problema permitem fluxo em ambos os sentidos. Portanto, quando lemos uma conexão entre $u$ e $v$, nós inicializamos cap[u][v] e cap[v][u] simultaneamente com a largura de banda dada.
3. Índices Base-0: O problema fornece os computadores numerados de 1 a n. Para mapeá-los corretamente na nossa matriz (que vai de 0 a n-1), subtraímos 1 dos valores lidos para a origem, destino e conexões.
