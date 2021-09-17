package gaetan.renault.mareu;

public class TestUtils {

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId){
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
