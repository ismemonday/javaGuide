[回到主目录](/README.md)
# java流处理Stream
## 不可变集合
```java
/**
*不可变集合
*/
class ImmutableCollections{}
//获取List不可变集合
List.of(E... e)
//获取Set不可变集合
Set.of(E... e)
//获取Map不可变集合
  //- 小于等于10元素
    Map.of(K k,V v)
  //- 大于10个元素
    Map.ofEntries(Entry<? exetends K,? extends V>... entries)
```
## 集合的copyOf()方法
```java
  //如果list是不可变的，返回的l是本身，如果list是可变的，返回一个新对象
  List l=List.copyOf(List list)
  //如果set是不可变的，返回的s是本身，如果set是可变的，返回一个新对象
  Set s=Set.copyOf(Set set)
  //如果map是不可变的，返回的m是本身，如果map是可变的，返回一个新对象
  Map m=Map.copyOf(Map map)

  //*值得注意的是，copyOf返回的集合也是不可变集合
```
### 集合转换
```java
  //map=>set
  HashMap map=new HashMap<>();
  Set<Map.entry> setEntry=map.entrySet();
  //set=>array
  //不指定转换类型
  Object[] objects=setEntry.toArray()
  //指定类型转换
  Map.Entry[] objects=setEntry.toArray(new Map.Entry[0])
  ArrayList[] lists=setEntry.toArray(new ArrayList[0])
```
## Stream流
### - 流获取
```java
  //1-集合获取流
    Collection.stream()
  //2-数组获取流
    Arrays.stream()
  //3-相同类型对象
    Stream.of(T... t)
    
```
### - 流操作
```java
//过滤
Stream.filter()
//操作
Stream.map(value-{return Result})
//合并
 Stream.concat(stream1,stream2 )
```
### - 流收集
```java
//收集
Stream.collect();
  //收集为list
 List<Integer> lists = Stream.of(1, 2, 3).collect(Collectors.toList());
 //收集为set
 Set<Integer> sets = Stream.of(1, 2, 3).collect(Collectors.toSet());
 //收集为map
 Map<Integer, Integer> collect = Stream.of(1, 2, 3).collect(Collectors.toMap(k -> k, (k)->1));
```
