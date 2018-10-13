package com.alta.hello.tools.uniqueid;

import com.alta.hello.tools.date.DateTimer;

/**
 * SnowId解析器.
 *
 * @author GD
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class IdParser {

    /**
     * 解析SnowID.
     *
     * <p>
     *     解析结果中为UTC0时区时间信息.
     * </p>
     * @param id 待解析UTC0时区时间戳
     * @return IdInfo对象
     */
    public static IdInfo parse(long id) {
        long time = id >> 22;
        String timeStr = DateTimer.utcLong2Str(time);
        long dataCenter = (id << 42) >> 59;
        long machineId = (id << 47) >> 59;
        long seq = (id << 52) >> 52;
        return new IdInfo().setTime(time)
                .setTimeStr(timeStr)
                .setDataCenter(dataCenter)
                .setMachineId(machineId)
                .setSeq(seq);
    }

    /**
     * Id信息.
     */
    public static class IdInfo {
        /** 时间戳 */
        private long time;
        /** 可读时间字符串 */
        private String timeStr;
        /** 数据中心ID */
        private long dataCenter;
        /** 机器码ID */
        private long machineId;
        /** 序列号 */
        private long seq;

        public long getTime() {
            return time;
        }

        public IdInfo setTime(long time) {
            this.time = time;
            return this;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public IdInfo setTimeStr(String timeStr) {
            this.timeStr = timeStr;
            return this;
        }

        public long getDataCenter() {
            return dataCenter;
        }

        public IdInfo setDataCenter(long dataCenter) {
            this.dataCenter = dataCenter;
            return this;
        }

        public long getMachineId() {
            return machineId;
        }

        public IdInfo setMachineId(long machineId) {
            this.machineId = machineId;
            return this;
        }

        public long getSeq() {
            return seq;
        }

        public IdInfo setSeq(long seq) {
            this.seq = seq;
            return this;
        }
    }

}
