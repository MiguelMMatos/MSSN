package Projeto.Boids;

public class DatabaseBoidDeaths {

    private int numberPredatoresDeath;
    private int numberPraysDeath;
    private int numberPredatoresOmastarDeath;
    private int numberPraysSquirtleDeath;

    public DatabaseBoidDeaths(){
        numberPredatoresDeath = 0;
        numberPraysDeath = 0;
    }

    public void increasePredatorDeath(){
        numberPredatoresDeath++;
    }

    public void increasePraysDeath(){
        numberPraysDeath++;
    }

    public void increasePraysSquirtleDeath(){
        numberPraysSquirtleDeath++;
    }

    public void increasePredatoresOmastarDeath(){
        numberPredatoresOmastarDeath++;
    }

    public int getPredatoresDeath(){
        return numberPredatoresDeath;
    }

    public int getPraysDeath(){
        return numberPraysDeath;
    }

    public int getPredatoresOmastarDeath(){
        return numberPredatoresOmastarDeath;
    }

    public int getPraysSquirtleDeath(){
        return numberPraysSquirtleDeath;
    }
}
