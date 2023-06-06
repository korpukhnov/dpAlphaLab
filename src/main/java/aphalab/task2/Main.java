package aphalab.task2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Main {

    public interface NamedObject {
        public String getName();
    }

    public static class NamedObjectImpl implements NamedObject {
        private String name;

        public NamedObjectImpl(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static class Grouper {
        public static Map<String, List<NamedObject>> groupNamedObjects(Collection<NamedObject> collection) {
            return collection.stream().collect(Collectors.groupingBy(NamedObject::getName));
        }
    }

    public static void main(String[] args) {
        NamedObject no1 = new NamedObjectImpl("name 1");
        NamedObject no2 = new NamedObjectImpl("name 1");
        NamedObject no3 = new NamedObjectImpl("name 3");
        Map<String, List<NamedObject>> res = Grouper.groupNamedObjects(List.of(no1, no2, no3));
        System.out.println(res);
    }
}