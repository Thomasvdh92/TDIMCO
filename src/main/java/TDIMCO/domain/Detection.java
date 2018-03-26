package TDIMCO.domain;

import java.time.LocalDateTime;

/**
 * Created by Thomas on 18-3-2018.
 */

public class Detection {

    private Detector detector;

    private LocalDateTime detectionDate;

    public Detection(Detector detector, LocalDateTime detectionDate) {
        this.detector = detector;
        this.detectionDate = detectionDate;
    }

    public Detector getDetector() {
        return detector;
    }

    public LocalDateTime getDetectionDate() {
        return detectionDate;
    }
}
