package top.lybysu.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author xyt
 * @date 2021/10/5
 */

@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
