<template>
    <div>
        <Number
            label="SubscriptionId"
            v-model="value.subscriptionId"
            :editMode="editMode"
        />
        <String
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <String
            label="Status"
            v-model="value.status"
            :editMode="editMode"
        />
        <Date
            label="StartedAt"
            v-model="value.startedAt"
            :editMode="editMode"
        />
        <Date
            label="EndsAt"
            v-model="value.endsAt"
            :editMode="editMode"
        />
        <Date
            label="LastRenewalAt"
            v-model="value.lastRenewalAt"
            :editMode="editMode"
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
    name: 'SubscribeManagement',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "subscribeManagements",
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
