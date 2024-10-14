package deque;
import java.util.Comparator;
public class MaxArrayDeque61B<T> extends ArrayDeque61B<T>{
    private final Comparator<T> comparator;

    public MaxArrayDeque61B(Comparator<T> c){
        this.comparator=c;
    }

    public T max(){
        if(size()==0) return null;
        T temp =get(0);
        for (int i =1 ; i<size();i++){
            if (this.comparator.compare(temp,get(i))<0) {
                temp = get(i);
            }
        }
        return temp;
    }

    public T max(Comparator<T> c) {
        if (size() == 0) return null;
        T temp = (T) get(0);
        for (int i = 1; i < size(); i++) {
            if (c.compare(temp,get(i)) < 0) {
                temp = get(i);

            }
        }
        return temp;
    }
}
