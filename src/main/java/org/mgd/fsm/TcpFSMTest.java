package org.mgd.fsm;

public class TcpFSMTest {
    public enum TcpConnectionState {
        CLOSED, LISTEN, SYN_SENT, ESTABLISHED, FIN_WAIT_1, FIN_WAIT_2, TIME_WAIT, LAST_ACK,CLOSE_WAIT
    }

    public class TcpConnectionStateMachine {
        private TcpConnectionState currentState;

        public TcpConnectionStateMachine() {
            this.currentState = TcpConnectionState.CLOSED;
        }

        // 发起连接
        public void initiateConnection() {
            if (currentState == TcpConnectionState.CLOSED) {
                currentState = TcpConnectionState.SYN_SENT;
                System.out.println("客户端发送SYN，进入SYN_SENT状态");
            } else {
                throw new IllegalStateException("当前无法发起新的连接");
            }
        }

        // 收到SYN并回复SYN+ACK
        public void receiveSyn() {
            if (currentState == TcpConnectionState.LISTEN) {
                currentState = TcpConnectionState.ESTABLISHED;
                System.out.println("服务器收到SYN，发送SYN+ACK，进入ESTABLISHED状态");
            } else if (currentState == TcpConnectionState.SYN_SENT) {
                currentState = TcpConnectionState.ESTABLISHED;
                System.out.println("客户端收到SYN+ACK，进入ESTABLISHED状态");
            } else {
                throw new IllegalStateException("非法状态，无法处理SYN");
            }
        }

        // 收到SYN+ACK
        public void receiveSynAck() {
            if (currentState == TcpConnectionState.SYN_SENT) {
                currentState = TcpConnectionState.ESTABLISHED;
                System.out.println("客户端收到SYN+ACK，进入ESTABLISHED状态");
            } else {
                throw new IllegalStateException("非法状态，无法处理SYN+ACK");
            }
        }

        // 主动关闭连接
        public void closeConnection() {
            if (currentState == TcpConnectionState.ESTABLISHED) {
                currentState = TcpConnectionState.FIN_WAIT_1;
                System.out.println("主动关闭方发送FIN，进入FIN_WAIT_1状态");
            } else {
                throw new IllegalStateException("当前不能关闭连接");
            }
        }

        // 收到FIN
        public void receiveFin() {
            if (currentState == TcpConnectionState.ESTABLISHED) {
                currentState = TcpConnectionState.CLOSE_WAIT;
                System.out.println("被动关闭方收到FIN，发送ACK，进入CLOSE_WAIT状态");
            } else if (currentState == TcpConnectionState.FIN_WAIT_1 || currentState == TcpConnectionState.FIN_WAIT_2) {
                currentState = TcpConnectionState.TIME_WAIT;
                System.out.println("主动关闭方收到FIN，发送ACK，进入TIME_WAIT状态");
            } else {
                throw new IllegalStateException("非法状态，无法处理FIN");
            }
        }

        // 发送FIN的ACK确认
        public void sendFinAck() {
            if (currentState == TcpConnectionState.ESTABLISHED || currentState == TcpConnectionState.CLOSE_WAIT) {
                currentState = (currentState == TcpConnectionState.ESTABLISHED) ? TcpConnectionState.LAST_ACK : TcpConnectionState.CLOSED;
                System.out.println("发送FIN的ACK确认，进入" + (currentState == TcpConnectionState.LAST_ACK ? "LAST_ACK" : "CLOSED") + "状态");
            } else {
                throw new IllegalStateException("当前状态下无法发送FIN的ACK确认");
            }
        }

        // 收到FIN的ACK确认
        public void receiveFinAck() {
            if (currentState == TcpConnectionState.FIN_WAIT_1 || currentState == TcpConnectionState.FIN_WAIT_2) {
                currentState = TcpConnectionState.TIME_WAIT;
                System.out.println("收到FIN的ACK确认，进入TIME_WAIT状态");
            } else if (currentState == TcpConnectionState.LAST_ACK) {
                currentState = TcpConnectionState.CLOSED;
                System.out.println("收到FIN的ACK确认，进入CLOSED状态");
            } else {
                throw new IllegalStateException("非法状态，无法处理FIN的ACK");
            }
        }
    }
}
