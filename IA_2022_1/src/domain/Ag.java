package domain;

import domain.factory.IndividuoFactory;
import domain.factory.IndividuoNRainhasFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Ag {

    public Individuo executar(int nPop, IndividuoFactory indFactory, int nElite, boolean isMax, int nGer) {
        // TODO for para criar nPop individuos em uma lista

        List<Individuo> indL = new LinkedList<>();
        List<Individuo> mutanteList = new LinkedList<>();
        List<Individuo> filhosList = new LinkedList<>();
        List<Individuo> joinPop = new LinkedList<>();
        List<Individuo> newPop = new LinkedList<>();

        for (int i = 0; i < 20; i++)
            indL.add(indFactory.getIndividuo());


        // TODO aleatoriamente selecionar duplas de indivíduos para o crossover
        for (int rep= 0; rep < nGer; rep++) {

            Random random = new Random();
            Map hash = new HashMap();
            int rand, nInd = indL.size();

            for (int i = 0; i < nInd; i++) {
                if (hash.get(i) == null) {
                    rand = random.nextInt(nInd);
                    while (hash.get(rand) != null || rand == i) {
                        rand = random.nextInt(nInd);
                    }
                    hash.put(rand, rand);
                    hash.put(i, i);
                    filhosList.addAll(indL.get(i).getFilhos(indL.get(rand)));
                    mutanteList.add(indL.get(i).getMutante());
                    mutanteList.add(indL.get(rand).getMutante());
                }
            }

            joinPop.addAll(indL);
            joinPop.addAll(filhosList);
            joinPop.addAll(mutanteList);

            for (Individuo ind : joinPop)
                ind.getAvaliacao();

            newPop.addAll(joinPop.stream().sorted(Comparator.reverseOrder()).limit(nElite).collect(Collectors.toList()));
            joinPop.removeAll(joinPop.stream().sorted(Comparator.reverseOrder()).limit(nElite).collect(Collectors.toList()));

            List<Individuo> restanteList = this.roleta(joinPop, nPop - nElite, isMax);
            newPop.addAll(restanteList);

            indL.clear();
            indL.addAll(newPop);

            newPop.clear();
            mutanteList.clear();
            filhosList.clear();
            joinPop.clear();

        }
        Individuo ind = null;
        for (int i = 0; i < indL.size(); i++) {
            if(i==0)
                ind = indL.get(i);
            else{
                if(ind.avaliacao < indL.get(i).avaliacao)
                    ind = indL.get(i);
            }
        }

        return ind;
    }

    private List<Individuo> roleta(List<Individuo> joinPop, int nRestantes, boolean isMax) {
        List<Individuo> plebe = new LinkedList<>();
        Random random = new Random();
        int ind;
        if (!isMax) {
            for (int i = 0; i < nRestantes; i++) {
                ind = random.nextInt(joinPop.size());
                plebe.add(joinPop.get(ind));
                joinPop.remove(ind);
            }
            return plebe;
        }
        return null;
    }


}
