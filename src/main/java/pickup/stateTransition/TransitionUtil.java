package pickup.stateTransition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by YongGang on 2017/1/22.
 */
public class TransitionUtil {

  // 状态变迁是否允许；
  // 如果 from/to 在 entries 中存在，则返回 true，否则 false；
  static Boolean isAllowed(final List<TransitionEntry> entries, final String fromState, final String toState) {
    Boolean rtn = false;

    for (TransitionEntry entry : entries) {
      if (entry.getFrom().equalsIgnoreCase(fromState) && entry.getTo().equalsIgnoreCase(toState)) {
        rtn = true;
        break;
      }
    }

    return rtn;
  }

  // 前一个是什么状态，才能到当前这个状态
  static List<String> getPreStates(final List<TransitionEntry> entries, final String toState) {

    List<String> rtn =
        entries.stream()
               .filter((entry) -> entry.getTo().equalsIgnoreCase(toState))
               .map(TransitionEntry::getFrom)
               .collect(Collectors.toList());

    return rtn;
  }


  // 当前这个状态，下一个状态可以是哪些
  static List<String> getNextStates(List<TransitionEntry> entries, String fromState) {

    List<String> rtn =
        entries.stream()
               .filter((entry) -> entry.getFrom().equalsIgnoreCase(fromState))
               .map(TransitionEntry::getTo)
               .collect(Collectors.toList());

    return rtn;
  }

  // 当前有多个状态，下一个可能的状态是什么
  static List<String> getAllNextStates(final List<TransitionEntry> entries, final List<String> from) {
    List<String> out = new ArrayList<>();

    // 传入状态 distinct
    List<String> distStates = from.stream().distinct().collect(Collectors.toList());

    int stateCount = distStates.size();

    if (stateCount == 0) {
      return out;
    }

    // 每个传入状态，可能的下一个状态
    List<String> allNext =
        distStates.stream()
                  .map((state) -> TransitionUtil.getNextStates(entries, state))
                  .flatMap(List::stream)
                  .collect(Collectors.toList());

    // 所有下一个状态的交集，就是共同的下一个可能状态
    // 下一个状态的出现的个数，等于 distinct 传入状态
    HashMap<String, Integer> stateMap = new HashMap<>();
    for (String st : allNext) {
      Integer n = stateMap.get(st);
      if (n == null) {
        stateMap.put(st, 1);
      } else {
        stateMap.put(st, n + 1);
      }
    }

    out =
        stateMap.entrySet()
                .stream()
                .filter((e) -> e.getValue() == stateCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    return out;
  }
}
