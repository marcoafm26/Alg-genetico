package domain;

import java.util.*;

public class IndividuoNRainhas extends Individuo {

    Random random;
    private int nRainhas;

    public IndividuoNRainhas(int nRainhas) {
        this.nRainhas = nRainhas;
        random = new Random();
        genes = geraGenes(this.nRainhas);
    }

    public IndividuoNRainhas(int nRainhas, List<Integer> genes) {
        this.nRainhas = nRainhas;
        random = new Random();
        this.genes = genes;
    }

    //TODO implementar os métodos da classe
    // numero de colisoes: diagonal
    @Override
    public Double avaliar() {
        double avaliacao = 0;
        int line;
        for (int col = 0; col <= genes.size() - 1; col++) {

            line = genes.get(col);

            if (col > 0) {
                if (genes.get(col - 1) == line - 1 || genes.get(col - 1) == line + 1)
                    avaliacao++;
            }
            if (col < genes.size() - 1) {
                if ((genes.get(col + 1) == line - 1) || (genes.get(col + 1) == line + 1)) {
                    avaliacao++;
                    col++;
                }
            }
        }

        return avaliacao;
    }

    private List<Integer> geraGenes(int nGenes) {
        Map hash = new HashMap();
        int rand;
        List<Integer> genes = new ArrayList<Integer>(nGenes);
        for (int i = 0; i < nGenes; i++) {
            rand = random.nextInt(nGenes);
            while (hash.get(rand) != null)
                rand = random.nextInt(nGenes);
            genes.add(rand);
            hash.put(rand, rand);

        }
        return genes;
    }

    @Override
    public List<Individuo> getFilhos(Individuo ind) {
        int rand = random.nextInt(nRainhas);
        List<Integer> genes = new ArrayList<>(this.nRainhas);
        List<Integer> genes2 = new ArrayList<>(this.nRainhas);
        List<Integer> genesNU = new ArrayList<>(this.nRainhas);
        List<Integer> genesNU2 = new ArrayList<>(this.nRainhas);
        List<Individuo> individuos = new LinkedList<>();

        for (int i = 0; i <= rand; i++) {
            genes.add(this.genes.get(i));
            genes2.add(ind.genes.get(i));
        }
        for (int i = rand + 1; i < this.genes.size(); i++) {
            genes.add(ind.genes.get(i));
            genes2.add(this.genes.get(i));
        }
        Map<Integer, ArrayList<Integer>> repetidos = new HashMap<>();
        Map<Integer, ArrayList<Integer>> repetidos2 = new HashMap<>();

        for (int i = 0; i < this.nRainhas; i++) {
            if (!genes.contains(i))
                genesNU.add(i);

            if (!genes2.contains(i))
                genesNU2.add(i);
        }

        for (int i = 0; i < genes.size(); i++) {
            if (repetidos.get(genes.get(i)) == null)
                repetidos.put(genes.get(i), new ArrayList<>());
            if (repetidos2.get(genes2.get(i)) == null)
                repetidos2.put(genes2.get(i), new ArrayList<>());
            repetidos.get(genes.get(i)).add(i);
            repetidos2.get(genes2.get(i)).add(i);
        }

        for (ArrayList<Integer> value : repetidos.values()) {
            int size = value.size();
            if (size >= 2) {
                genes.set(value.get(random.nextInt(size)), genesNU.remove(random.nextInt(genesNU.size())));
            }
        }
        for (ArrayList<Integer> value : repetidos2.values()) {
            int size = value.size();
            if (size >= 2)
                genes2.set(value.get(random.nextInt(size)), genesNU2.remove(random.nextInt(genesNU2.size())));
        }

        individuos.add(new IndividuoNRainhas(this.nRainhas, genes));
        individuos.add(new IndividuoNRainhas(this.nRainhas, genes2));
        return individuos;
    }

    @Override
    public Individuo getMutante() {
        List<Integer> genes = new ArrayList<>();
        genes.addAll(this.genes);

        int pos1 = random.nextInt(nRainhas - 1);
        int pos2 = random.nextInt(nRainhas - 1);
        int geneMutante = genes.get(pos1);

        genes.set(pos1, genes.get(pos2));
        genes.set(pos2, geneMutante);

        return new IndividuoNRainhas(this.nRainhas, genes);
    }

    @Override
    public String toString() {
        return String.format("nRainhas: %s \nAvaliação: %s", nRainhas, avaliacao);
    }

    @Override
    public int compareTo(Individuo o) {
        if (this.avaliacao < o.avaliacao)
            return -1;
        if (this.avaliacao > o.avaliacao)
            return 1;
        return 0;
    }


}
