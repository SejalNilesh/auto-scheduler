import java.util.*;

public class GraphColoringSolver {

    public Map<String, Integer> colorGraph(Map<String, Set<String>> graph) {
        Map<String, Integer> result = new HashMap<>();

        for (String node : graph.keySet()) {
            Set<Integer> usedColors = new HashSet<>();
            for (String neighbor : graph.get(node)) {
                if (result.containsKey(neighbor)) {
                    usedColors.add(result.get(neighbor));
                }
            }

            int color = 0;
            while (usedColors.contains(color)) {
                color++;
            }

            result.put(node, color);
        }

        return result;
    }
}
