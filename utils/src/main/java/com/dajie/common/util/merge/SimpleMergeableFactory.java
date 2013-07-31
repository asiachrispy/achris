package com.dajie.common.util.merge;

import com.dajie.common.util.merge.impl.*;

import java.util.*;

/**
 * User:tao.li
 * Date: 13-3-13
 * Time: 下午8:22
 */
public class SimpleMergeableFactory implements MergeableFactory {
    private final Map<Class, MergeCreator> typeMergeMapping = new HashMap<Class, MergeCreator>();

    public SimpleMergeableFactory() {
        typeMergeMapping.put(Integer.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableInteger();
            }
        });

        typeMergeMapping.put(Integer.TYPE, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableInteger();
            }
        });

        typeMergeMapping.put(Long.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableLong();
            }
        });

        typeMergeMapping.put(Long.TYPE, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableLong();
            }
        });

        typeMergeMapping.put(Set.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableSet();
            }
        });
        typeMergeMapping.put(HashSet.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableSet(new HashSet());
            }
        });
        typeMergeMapping.put(TreeSet.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableSet(new TreeSet());
            }
        });
        typeMergeMapping.put(LinkedHashSet.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableSet(new LinkedHashSet());
            }
        });

        typeMergeMapping.put(List.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableList();
            }
        });
        typeMergeMapping.put(ArrayList.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableList(new ArrayList());
            }
        });
        typeMergeMapping.put(LinkedList.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableList(new LinkedList());
            }
        });

        typeMergeMapping.put(Map.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableMap();
            }
        });
        typeMergeMapping.put(HashMap.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableMap(new HashMap());
            }
        });
        typeMergeMapping.put(TreeMap.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableMap(new TreeMap());
            }
        });
        typeMergeMapping.put(LinkedHashMap.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableMap(new LinkedHashMap());
            }
        });
        typeMergeMapping.put(Collection.class, new MergeCreator() {
            @Override
            public Mergeable create() {
                return new MergeableList();
            }
        });
    }

    public void setTypeMergeMapping(Map<Class, MergeCreator> typeMergeMapping) {
        if (typeMergeMapping != null && !typeMergeMapping.isEmpty()) {
            this.typeMergeMapping.putAll(typeMergeMapping);
        }
    }


    @Override
    public <T> Mergeable<T> getMergeForType(Class<T> cls) {
        MergeCreator mergeCreator = this.typeMergeMapping.get(cls);
        if (mergeCreator != null) {
            return mergeCreator.create();
        }
        return null;
    }

    interface MergeCreator<T> {
        Mergeable<T> create();
    }
}
