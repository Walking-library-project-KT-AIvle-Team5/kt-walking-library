<template>
    <div>
        <Number
            label="PointRequestId"
            v-model="value.pointRequestId"
            :editMode="editMode"
        />
        <String
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <Number
            label="RequestedPointAmount"
            v-model="value.requestedPointAmount"
            :editMode="editMode"
        />
        <String
            label="PaymentMethodId"
            v-model="value.paymentMethodId"
            :editMode="editMode"
        />
        <String
            label="Status"
            v-model="value.status"
            :editMode="editMode"
        />
        <Date
            label="Timestamp"
            v-model="value.timestamp"
            :editMode="editMode"
        />
        <Number
            offline
            label="ActualPaymentAmount"
            v-model="value.actualPaymentAmount"
            :editMode="editMode"
            @change="change"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'PointRequestManagement',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "pointRequestManagements",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
