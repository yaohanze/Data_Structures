package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.proj2c.MyTrieSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private WeirdPointSet wPs;
    private HashMap<Point, Node> poinTnode;
    private MyTrieSet trieName;
    private HashMap<String, ArrayList<Node>> nameMap;
    private HashMap<String, HashSet<String>> uniqueName;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        HashMap<Point, Node> pointNode = new HashMap<>();
        List<Point> point = new ArrayList<>();
        for (Node n: nodes) {
            if (neighbors(n.id()).isEmpty()) {
                continue;
            }
            Point p = new Point(n.lon(), n.lat());
            point.add(p);
            pointNode.put(p, n);
        }
        wPs =  new WeirdPointSet(point);
        poinTnode = pointNode;
        MyTrieSet trie = new MyTrieSet();
        HashMap<String, ArrayList<Node>> map = new HashMap<>();
        HashMap<String, HashSet<String>> unique = new HashMap<>();
        for (Node n: nodes) {
            if (n.name() == null) {
                continue;
            }
            String cleanedName = cleanString(n.name());
            if (!trie.contains(cleanedName)) {
                trie.add(cleanedName);
            }
            if (map.containsKey(cleanedName)) {
                map.get(cleanedName).add(n);
            } else {
                ArrayList<Node> node = new ArrayList<>();
                node.add(n);
                map.put(cleanedName, node);
            }
            if (unique.containsKey(cleanedName)) {
                unique.get(cleanedName).add(n.name());
            } else {
                HashSet<String> nm = new HashSet<>();
                nm.add(n.name());
                unique.put(cleanedName, nm);
            }
        }
        trieName = trie;
        nameMap = map;
        uniqueName = unique;
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point nearestPoint = wPs.nearest(lon, lat);
        Node nearestNode = poinTnode.get(nearestPoint);
        return nearestNode.id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> withPrefix = trieName.keysWithPrefix(prefix);
        List<String> result = new ArrayList<>();
        for (String s: withPrefix) {
            HashSet<String> nameSet = uniqueName.get(s);
            for (String name: nameSet) {
                result.add(name);
            }
        }
        return result;
     }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanedName = cleanString(locationName);
        List<Node> nodeList = nameMap.get(cleanedName);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Node node: nodeList) {
            Map<String, Object> m = new HashMap<>();
            m.put("lat", node.lat());
            m.put("lon", node.lon());
            m.put("name", node.name());
            m.put("id", node.id());
            result.add(m);
        }
        return result;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
