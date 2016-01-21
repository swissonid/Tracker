package ch.swissonid.tracker.services.recorder


interface RecorderService {
    fun startRecording(trailName:String)
    fun stopRecording()
}