package sql.sqlcenter;

public interface iObservable {
    public void addObserver(iObserver obs);
    public void deleteObserver(iObserver obs);
    public void notifyObservers(String data);
}
