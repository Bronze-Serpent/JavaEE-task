package barabanov.entity;

public class Progress
{
    private final IDToken token;
    private final long score;
    private final long maxScore;


    public Progress(IDToken token, long score, long maxScore)
    {
        this.token = token;
        this.score = score;
        this.maxScore = maxScore;
    }

    @Override
    public String toString() {
        return "Progress{" +
                token +
                ", score=" + score +
                ", maxScore=" + maxScore +
                '}';
    }

    public IDToken getToken() {return token;}

    public long getScore() {return score;}

    public long getMaxScore() {return maxScore;}
}
