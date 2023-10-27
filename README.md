# simple-string-netty
* 문자열 기반 네티 서버, 클라이언트 샘플
* ServerAgent
  * 서버
  * 예외가 발생하지만 재기동
  * 문자열은 전송할 때마다 숫자를 붙여 다르게 전송.
  * 채널별로 문자열을 10만개 받게 되면 channel close. 이후 재접속.
  * 60초 동안 allIdle상태가 되면 heartbeat 메세지 전송. 전송시 오류가 발생하면 channel close
* ClientAgent
  * 클라이언트
  * 서버와 접속이 끊어져도 재접속
  * 서버접속시에 최초로 문자열 전송.   

* TODO
  * ByteToMessageDecoder 샘플 추가
  * ReplayingDecoder 샘플 추가
  * FSM 을 이용하여 복잡한 비즈니스 로직 구현 샘플
