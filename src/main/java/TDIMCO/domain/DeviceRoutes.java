package TDIMCO.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
public class DeviceRoutes {

    private Device device;

    private List<DeviceRoute> routes;

    public DeviceRoutes(Device device) {
        this.device = device;
        routes = new ArrayList<>();
    }

    public void addRoute(LocalDateTime ldt, Route r) {
        if(routes == null) {
            routes = new ArrayList<>();
        }
        routes.add(new DeviceRoute(ldt, r));
    }

    @Data
    public class DeviceRoute {
        private LocalDateTime ldt;
        private Route route;

        public DeviceRoute(LocalDateTime ldt, Route route) {
            this.ldt = ldt;
            this.route = route;
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o instanceof DeviceRoute) {
                DeviceRoute d = (DeviceRoute) o;
                return this.ldt == d.ldt && this.route == d.route;
            }
            return false;
        }
    }


}
